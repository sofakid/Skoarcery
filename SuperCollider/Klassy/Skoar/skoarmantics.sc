// ============
// Skoarmantics
// ============

/*

This code is applied during the decoration stage of compiling the skoar tree.

For stuff to happen during performing the tree, we set handlers here.

We also shrink the tree, drop some punctuation noads;
   when you see replace_children, that's what's going on. 

absorb_toke assumes the only child is a toke, puts it in 
   noad.toke and removes the child. (and returns the toke)

We went depth first and run the code on the way back,
   so children are processed first.

*/
Skoarmantics {

    *new {

        var dict = Dictionary[

            "branch" -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                noad.branch = noad;

                if (x != nil && x.toke != nil) {
                    x = x.toke;

                    if (x.isKindOf(Toke_Voice)) {
                        noad.toke = x;
                        noad.voice = skoar.get_voice(x.val);
                    };
                };

                // drop the newline
                noad.children.pop;
                noad.n = n - 1;

            },

            "msg_chain_node" -> {
                | skoar, noad |
            },
        
            "beat" -> {
                | skoar, noad |
                var t = noad.absorb_toke;

                noad.beat = t;
                noad.is_beat = true;
                noad.is_rest = t.is_rest;
            },

            "listy" -> {
                | skoar, noad |

                var n = noad.children.size;
                var x = nil;
                var items = List.new;

                // skip the first and last tokens
                for (1, n - 2, {
                    | i |
                    x = noad.children[i];

                    // skip the separators
                    if (x.toke.isKindOf(Toke_ListSep) == false) {
                        items.add(x);
                    };
                });

                noad.replace_children(items);

            },
        
            "clef" -> {
                | skoar, noad |
            },

            "stmt" -> {
                | skoar, noad |
            },
        
            "musical_keyword_misc" -> {
                | skoar, noad |

                var toke = noad.absorb_toke;

                if (toke.isKindOf(Toke_Carrot)) {
                    noad.one_shots = {"TODO stress noat".postln;};
                };

            },

            "accidentally" -> {
                | skoar, noad |
            },

            "boolean" -> {
                | skoar, noad |
            },

            "ottavas" -> {
                | skoar, noad |

                var toke;

                toke = noad.absorb_toke;

                if (toke.isKindOf(Toke_OctaveShift)) {
                    noad.performer = {noad.voice.octave_shift(toke.val);};
                };

                if (toke.isKindOf(Toke_OttavaA)) {
                    noad.performer = {noad.voice.octave_shift(1);};
                };

                if (toke.isKindOf(Toke_OttavaB)) {
                    noad.performer = {noad.voice.octave_shift(-1);};
                };

                if (toke.isKindOf(Toke_QuindicesimaA)) {
                    noad.performer = {noad.voice.octave_shift(1);};
                };

                if (toke.isKindOf(Toke_QuindicesimaB)) {
                    noad.performer = {noad.voice.octave_shift(-1);};
                };


            },

            "assignment" -> {
                | skoar, noad |

                // the settable
                var y = nil;
                var y_toke = nil;

                y = noad.children[1];
                y_toke = y.toke;

                // we prepare the destination here (noad.setter), we'll setup the write in skoaroid

                // set a value on voice's skoarboard, keyed by a symbol
                if (y_toke.isKindOf(Toke_Symbol)) {
                    noad.setter = {
                        | x, v |
                        v.assign_symbol(x, y_toke);
                    };
                };

                // set tempo
                if (y_toke.isKindOf(Toke_Quarters) || y_toke.isKindOf(Toke_Eighths)) {
                    noad.setter = {
                        | x, v |
                        var x_toke = x.next_toke;

                        if (x_toke.isKindOf(Toke_Int) || x_toke.isKindOf(Toke_Float)) {
                            v.set_tempo(x_toke.val, y_toke);
                        } {
                            SkoarError("Tried to use a " ++ x_toke.name ++ " for tempo.").throw;
                        };
                    };
                };

            },

            "skoaroid" -> {
                | skoar, noad |

                var f = nil;
                var x = nil;
                var y = nil;

                if (noad.children.size > 1) {
                    x = noad.children[0];
                    y = noad.children[1];

                    if (y.name == "assignment") {

                        f = y.setter;

                        noad.performer = {
                            | m |
                            f.(x, m.voice);
                        };

                    };
                };

            },
        
            "msg" -> {
                | skoar, noad |
            },

            "cthulhu" -> {
                | skoar, noad |
                noad.name = "^^(;,;)^^";
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            "dynamic" -> {
                | skoar, noad |
                var toke = noad.absorb_toke;
                noad.performer = {
                    | m |
                    m.voice.dynamic(toke);
                };
            },

            "dal_goto" -> {
                | skoar, noad |

                var toke = noad.children[0].toke;
                var al_x = noad.children[1].toke;
                var al_fine = false;

                if (al_x != nil && al_x.isKindOf(Toke_AlFine)) {
                    al_fine = true;
                };

                if (toke.isKindOf(Toke_DaCapo)) {
                    noad.performer = {
                        | m |
                        m.al_fine = al_fine;
                        SkoarDaCapoException.throw;
                    };
                };

                if (toke.isKindOf(Toke_DalSegno)) {
                    noad.performer = {
                        | m |
                        m.al_fine = al_fine;
                        SkoarSegnoException.throw;
                    };
                };

            },

            "al_x" -> {
                | skoar, noad |

                noad.absorb_toke;
            },

            "marker" -> {
                | skoar, noad |

                var toke = noad.absorb_toke;

                if (toke != nil) {

                    if (toke.isKindOf(Toke_Bars)) {

                        if (toke.pre_repeat) {
                            noad.performer = {
                                | m |

                                if (m.colons_burned.falseAt(noad)) {
                                    m.colons_burned[noad] = true;
                                    SkoarJumpException.throw;
                                };

                                if (toke.post_repeat) {
                                    m.colon_seen = noad;
                                };

                            };
                        } {
                            if (toke.post_repeat) {
                                noad.performer = {
                                    | m |
                                    m.colon_seen = noad;
                                };
                            };
                        };

                    };

                    if (toke.isKindOf(Toke_Segno)) {
                        noad.performer = {
                            | m |
                            m.segno_seen = noad;

                        };
                    };

                    if (toke.isKindOf(Toke_Fine)) {
                        noad.performer = {
                            | m |
                            if (m.al_fine) {
"SkoarFineException.throw;".postln;
                                SkoarFineException.throw;
                            };

                        };
                    };
                };
            },

            "coda" -> {
                | skoar, noad |
            },

            "noaty" -> {
                | skoar, noad |
            },
        
            "noat_literal" -> {
                | skoar, noad |
        
                var noat = noad.absorb_toke;
                noad.noat = noat;
        
                if (noat.isKindOf(Toke_NamedNoat)) {
                    noad.performer = {noad.voice.noat_go(noat)};
                };

                if (noat.isKindOf(Toke_Choard)) {
                    noad.performer = {noad.voice.choard_go(noat)};
                };
            },
        
            "noat_reference" -> {
                | skoar, noad |

                var x = noad.children[0];

                // TODO Symbol | CurNoat | listy
                if (x.name == "listy") {
                    x.performer = {noad.voice.choard_listy(x)};
                };

                if (x.name == "CurNoat") {
                    x.performer = {noad.voice.reload_curnoat(x)};
                };

                if (x.name == "Symbol") {
                    x.performer = {noad.voice.noat_symbol(x)};
                };

            },
        
            "pedally" -> {
                | skoar, noad |

                if (noad.toke.isKindOf(Toke_PedalUp)) {
                    noad.performer = {noad.voice.pedal_up;};
                };

                if (noad.toke.isKindOf(Toke_PedalDown)) {
                    noad.performer = {noad.voice.pedal_down;};
                };

            }

        ];
        ^dict;
    }

}