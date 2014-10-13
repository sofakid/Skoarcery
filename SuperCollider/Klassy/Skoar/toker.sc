
// =========
// The Toker
// =========

Toker {
    var skoarse;
    var i_am_here;
    var i_saw;

    *new {
        | code |
        ^super.new.init( code );
    }

    init {
        | code |
        skoarse = code;
        i_am_here = 0;
        i_saw = nil;
    }

    see {
        | want |

        if (i_saw != nil) {
            if (i_saw.isKindOf(want)) {
                ^i_saw
            }
        } {
            i_am_here = i_am_here + Toke_Whitespace.burn(skoarse, i_am_here);
            i_saw = want.match(skoarse, i_am_here);
            ^i_saw;
        }

        ^nil;
    }

    sees {
        | wants |

        var x = block {
            | break |

            wants.do {
                | want |

                x = this.see(want);
                if (x != nil) {
                    break.(x);
                };
            };

            break.(nil);
        };
        ^x;
    }

    burn {
        | want |

        var toke = i_saw;

        if (toke == nil) {
            toke = this.see(want);
        };

        if (toke.isKindOf(want)) {
            i_saw = nil;
            i_am_here = i_am_here + toke.burn;
            i_am_here = i_am_here + Toke_Whitespace.burn(skoarse, i_am_here);
            ^toke;
        };

        SkoarError("Burned wrong toke").throw;
    }

    eof {
        Toke_EOF.burn(skoarse, i_am_here);
    }

    dump {
        ("\nToker Dump" ++
        "\nhere   : " ++ i_am_here.asString ++
        "\nsaw    : " ++ i_saw.asString ++
        "\nskoarse: " ++ skoarse.copyRange(0,i_am_here)
                      ++ "_$_" ++ skoarse.copyRange(i_am_here, skoarse.size)).postln;
    }

}
