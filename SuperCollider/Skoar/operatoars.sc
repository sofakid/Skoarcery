SkoarOps {

    var <assignment;

    var <addition;
    var <multiplication;
    var <division;

    *new { ^super.new.init; }

    init {

        // v => settable
        assignment = (
        
            // settable
            SkoarpuscleSymbol: (

                // v
                Any: {
                    | minstrel, v, symbol |
                    var x = v.flatten(minstrel);
                    minstrel.koar[symbol.val] = x;
                    x
                }

            ),

            // settable
            SkoarpuscleBeat: (

                // v - the bpm
                SkoarpuscleInt:   {
                    | minstrel, bpm, beat |
                    var x = bpm.val / 60 * beat.val;
                    minstrel.koar[\tempo] = x;
                    SkoarpuscleInt(x)
                },

                SkoarpuscleFloat: {
                    | minstrel, bpm, beat |
                    var x = bpm.val / 60.0 * beat.val;
                    minstrel.koar[\tempo] = x;
                    SkoarpuscleFloat(x)
                }

            )
        );

        // x + y, x add y
        addition = (

            // x
            SkoarpuscleInt: (

                // y
                SkoarpuscleInt:   { | x, y | SkoarpuscleInt(x.val + y.val)   },
                SkoarpuscleFloat: { | x, y | SkoarpuscleFloat(x.val + y.val) },
                SkoarpuscleArray: { | x, y | SkoarpuscleArray([x] ++ y.val)  }

            ),

            // x
            SkoarpuscleFloat: (

                // y
                SkoarpuscleInt:   { | x, y | SkoarpuscleFloat(x.val + y.val); },
                SkoarpuscleFloat: { | x, y | SkoarpuscleFloat(x.val + y.val); },
                SkoarpuscleArray: { | x, y | SkoarpuscleArray([x] ++ y.val);  }
            )
        );

        // x mul y
        multiplication = (

            // x
            SkoarpuscleInt: (

                // y
                SkoarpuscleInt:   { | x, y | SkoarpuscleInt(x.val * y.val);   },
                SkoarpuscleFloat: { | x, y | SkoarpuscleFloat(x.val * y.val); }

            ),

            // x
            SkoarpuscleFloat: (

                // y
                SkoarpuscleInt:   { | x, y | SkoarpuscleFloat(x.val * y.val); },
                SkoarpuscleFloat: { | x, y | SkoarpuscleFloat(x.val * y.val); }
            )
        );

    }

    lookup {
        | op, x, y |

        var table = op[x.class.asSymbol];
        var f = table[y.class.asSymbol];

        if (f.isNil) {
            f = table[\Any];
        };

        if (f.notNil) {
            ^f
        };

        SkoarError("Lookup fail.").throw;
    }

    // v => settable
    assign {
        | minstrel, v, settable |
        var f = this.lookup(assignment, settable, v);
        "assign ".post; v.postln;
        ^minstrel.fairy.impress(f.(minstrel, v, settable));
    }

    // x + y
    add {
        | minstrel, x, y |
        var f = this.lookup(addition, x, y);
        "snerp".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }

    // x by y
    multiply {
        | minstrel, x, y |
        var f = this.lookup(multiplication, x, y);
        "x by y".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }


}