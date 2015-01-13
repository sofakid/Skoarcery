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
                    minstrel.koar[symbol.val] = v.flatten(minstrel);
                }

            ),

            // settable
            SkoarpuscleBeat: (

                // v
                SkoarpuscleInt:   { | minstrel, bpm, beat | minstrel.koar[\tempo] = bpm.val / 60   * beat.val; },
                SkoarpuscleFloat: { | minstrel, bpm, beat | minstrel.koar[\tempo] = bpm.val / 60.0 * beat.val; }

            )
        );

        // x + y, x add y
        addition = (

            // x
            SkoarpuscleInt: (

                // y
                SkoarpuscleInt:   { | x, y | ^SkoarpuscleInt(x.val + y.val); },
                SkoarpuscleFloat: { | x, y | ^SkoarpuscleFloat(x.val + y.val); },
                SkoarpuscleArray: { | x, y | ^SkoarpuscleArray([x] ++ y.val); }

            ),

            // x
            SkoarpuscleFloat: (

                // y
                SkoarpuscleInt:   { | x, y | ^SkoarpuscleFloat(x.val + y.val); },
                SkoarpuscleFloat: { | x, y | ^SkoarpuscleFloat(x.val + y.val); },
                SkoarpuscleArray: { | x, y | ^SkoarpuscleArray([x] ++ y.val); }
            )
        );

        // x mul y
        multiplication = (

            // x
            SkoarpuscleInt: (

                // y
                SkoarpuscleInt:   { | x, y | ^SkoarpuscleInt(x.val * y.val); },
                SkoarpuscleFloat: { | x, y | ^SkoarpuscleFloat(x.val * y.val); }

            ),

            // x
            SkoarpuscleFloat: (

                // y
                SkoarpuscleInt:   { | x, y | ^SkoarpuscleFloat(x.val * y.val); },
                SkoarpuscleFloat: { | x, y | ^SkoarpuscleFloat(x.val * y.val); }
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
        var koar = minstrel.koar;
        ^minstrel.fairy.impress(f.(minstrel, v, settable));
    }

    // x + y
    add {
        | minstrel, x, y |
        var f = this.lookup(addition, x, y);

        ^minstrel.fairy.impress(f.(x, y));
    }

    // x + y
    multiply {
        | minstrel, x, y |
        var f = this.lookup(multiplication, x, y);
        ^minstrel.fairy.impress(f.(x, y));
    }


}