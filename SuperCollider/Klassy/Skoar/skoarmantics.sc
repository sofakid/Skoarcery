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

            "skoar_line" -> {
                | skoar, noad |

                var n = 0;
                var x = nil;

                n = noad.n;
                x = noad.children[0];

                if (x != nil && x.isKindOf(Toke_Voice)) {
                    noad.toke = x;
                    noad.voice = skoar.get_noad.voice(x.toke.val);
                    "Voice: ".post; x.toke.val.postln;
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
                var t;

                "FLIBBITY".postln;
                noad.absorb_toke;
                "FLOPPITY".postln;
                t = noad.toke;
                "FELICITY".postln;

                noad.beat = t;
                noad.is_beat = true;
                noad.is_rest = t.is_rest;
                "JONES".postln;
            },
        
            "meter_beat" -> {
                | skoar, noad |
        
                noad.absorb_toke;
                noad.beat = noad.toke;
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
        
            "meter_symbolic" -> {
                | skoar, noad |
            },
        
            "stmt" -> {
                | skoar, noad |
            },
        
            "musical_keyword_misc" -> {
                | skoar, noad |
            },

            "meter_ass" -> {
                | skoar, noad |

                // the settable
                var y = nil;
                var toke = nil;

                y = noad.children[1];
                toke = y.toke;

                // we prepare the destination here, we'll setup the write in skoaroid
                if (toke.isKindOf(Toke_Quarters) || toke.isKindOf(Toke_Eighths)) {
                    noad.setter = {
                        | x |
                        var x_toke = x.toke;

                        if (x_toke.isKindOf(Toke_Int) || x_toke.isKindOf(Toke_Float)) {
                            noad.voice.set_tempo(x_toke.val, toke);
                        };
                    };
                };
            },

            "meter_stmt" -> {
                | skoar, noad |

                var f = nil;
                var x = nil;
                var y = nil;

                if (noad.children.size > 1) {
                    x = noad.children[0];
                    y = noad.children[1];

                    if (y.name == "meter_ass") {

                        f = y.setter;

                        noad.performer = {
                            f.(x);
                        };

                    };
                };
            },

            "assignment" -> {
                | skoar, noad |

                // the settable
                var y = nil;

                y = noad.children[1];

                // we prepare the destination here, we'll setup the write in skoaroid
                if (y.toke.isKindOf(Toke_Symbol)) {
                    noad.setter = {
                        | x |
                        noad.voice.assign_symbol(x, y.toke);
                    };
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
                            f.(x);
                        };

                    };
                };

            },
        
            "msg" -> {
                | skoar, noad |
            },

            "cthulhu" -> {
                | skoar, noad |
                noad.performer = {skoar.cthulhu(noad);};
            },
        
            "dynamic" -> {
                | skoar, noad |
                var toke = noad.absorb_toke;
                noad.performer = {noad.voice.dynamic(toke);};
            },
        
            "optional_carrots" -> {
                | skoar, noad |
            },

            "meter_sig_prime" -> {
                | skoar, noad |
            },
        
            "meter" -> {
                | skoar, noad |

                var n = noad.children.size;

                // trim start and end tokens
                noad.replace_children(noad.children.copyRange(1, n - 2));
            },


            "dal_goto" -> {
                | skoar, noad |

                var toke = noad.children[0].toke;
                skoar.do_when_voices_ready({noad.voice.add_marker(noad);});

                if (toke.isKindOf(Toke_DaCapo)) {
                    noad.performer = {noad.voice.da_capo(noad);};
                };

                if (toke.isKindOf(Toke_DalSegno)) {
                    noad.performer = {noad.voice.dal_segno(noad);};
                };

            },

            "marker" -> {
                | skoar, noad |
        
                var toke;

                noad.absorb_toke;
                skoar.do_when_voices_ready({noad.voice.add_marker(noad);});

                toke = noad.toke;
                if (toke != nil && toke.isKindOf(Toke_Bars)) {
                    if (toke.pre_repeat) {
                       noad.performer = {noad.voice.jmp_colon(noad);};
                    };

                };

            },

            "coda" -> {
                | skoar, noad |
                skoar.do_when_voices_ready({noad.voice.add_coda(noad);});
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