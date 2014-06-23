// ============
// Skoarmantics
// ============

//
// We went depth first so our children should be defined
//
Skoarmantics {

    *new {

        var dict = Dictionary[

            "msg_chain_node" -> {
                | skoar, noad |
            },
        
            "beat" -> {
                | skoar, noad |
                var t;
        
                noad.absorb_toke;
                t = noad.toke;

                noad.beat = t;
                noad.is_beat = true;
                noad.is_rest = t.is_rest;
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
                var items = List[];

                for (1, n {
                    | i |
                    x = noad.children[i];

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
                            skoar.set_tempo(x_toke.val, toke);
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
                        skoar.assign_symbol(x, y.toke);
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
                    noad.performer = {skoar.octave_shift(toke.val);};
                };

                if (toke.isKindOf(Toke_OttavaA)) {
                    noad.performer = {skoar.octave_shift(1);};
                };

                if (toke.isKindOf(Toke_OttavaB)) {
                    noad.performer = {skoar.octave_shift(-1);};
                };

                if (toke.isKindOf(Toke_QuindicesimaA)) {
                    noad.performer = {skoar.octave_shift(1);};
                };

                if (toke.isKindOf(Toke_QuindicesimaB)) {
                    noad.performer = {skoar.octave_shift(-1);};
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
            },
        
            "dynamic" -> {
                | skoar, noad |
                var toke = noad.absorb_toke;
                noad.performer = {skoar.dynamic(toke);};
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
                skoar.add_marker(noad);

                if (toke.isKindOf(Toke_DaCapo)) {
                    noad.performer = {skoar.da_capo(noad);};
                };

                if (toke.isKindOf(Toke_DalSegno)) {
                    noad.performer = {skoar.dal_segno(noad);};
                };

            },

            "marker" -> {
                | skoar, noad |
        
                var toke;
        
                noad.absorb_toke;
                skoar.add_marker(noad);
        
                toke = noad.toke;
                if (toke.isKindOf(Toke_Bars) && toke.pre_repeat) {
                    noad.performer = {skoar.jmp_colon(noad);};
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
                    noad.performer = {skoar.noat_go(noat)};
                };

                if (noat.isKindOf(Toke_Choard)) {
                    noad.performer = {skoar.choard_go(noat)};
                };
            },
        
            "noat_reference" -> {
                | skoar, noad |
        
                // TODO Symbol | CurNoat | listy
                if (noad.name == "listy") {
                    noad.performer = {skoar.choard_listy(noad)};
                };

                if (noad.name == "CurNoat") {
                    noad.performer = {skoar.reload_curnoat(noad)};
                };

                if (noad.name == "Symbol") {
                    noad.performer = {skoar.noat_symbol(noad)};
                };

            },
        
            "pedally" -> {
                | skoar, noad |

                if (noad.toke.isKindOf(Toke_PedalUp)) {
                    noad.performer = {skoar.pedal_up;};
                };

                if (noad.toke.isKindOf(Toke_PedalDown)) {
                    noad.performer = {skoar.pedal_down;};
                };

            }

        ];
        ^dict;
    }

}