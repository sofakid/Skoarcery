SkoarOps {

    var <assignment;

    var <addition;
    var <multiplication;
    var <division;

    *new { ^super.new.init; }

    init {

		// -----------------
		// Assignment Tables
		// -----------------
        // v => settable
        assignment = (
        
            // settable
            SkoarpuscleSymbol: (

                // v
                Any: {
                    | minstrel, v, symbol |
                    minstrel.koar[symbol.val] = v;
                    v
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

		// ---------------
		// Addition Tables
		// ---------------
		// x + y, x add y
        addition = (

			SkoarpuscleCrap: ( Any: { | x, y | SkoarpuscleCrap.new } ),

            // x +
            SkoarpuscleInt: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleInt(x.val + y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }

            ),

            // x +
            SkoarpuscleFloat: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val + y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }
            ),

			// x +
            SkoarpuscleHashLevel: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val + y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }
            ),

			SkoarpuscleList: (
				// y
                SkoarpuscleInt:     { | x, y | SkoarpuscleList(x.val ++ [y]) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleList(x.val ++ [y]) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList(x.val ++ [y]) },

				SkoarpuscleLies:    { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleString:  { | x, y | SkoarpuscleList(x.val ++ [y]) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList(x.val ++ y.val)}
			),

			SkoarpuscleNoat: (
				SkoarpuscleInt:     { | x, y | x.raiseBy(y) },
                SkoarpuscleFloat:   { | x, y | x.raiseBy(y) },
				SkoarpuscleHashLevel:   { | x, y | x.raiseBy(y) },
				SkoarpuscleFreq:    { | x, y | x.raiseBy(y) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }
			),

			SkoarpuscleChoard: (
				SkoarpuscleInt:     { | x, y | x.raiseBy(y) },
                SkoarpuscleFloat:   { | x, y | x.raiseBy(y) },
				SkoarpuscleHashLevel:   { | x, y | x.raiseBy(y) },
				SkoarpuscleFreq:    { | x, y | x.raiseBy(y) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }
			),

			SkoarpuscleLies: (
				SkoarpuscleInt:     { | x, y | x },
                SkoarpuscleFloat:   { | x, y | x },
				SkoarpuscleHashLevel:   { | x, y | x },
				SkoarpuscleFreq:    { | x, y | x },
				SkoarpuscleNoat:    { | x, y | x },
				SkoarpuscleSymbol:  { | x, y | x },
				SkoarpuscleString:  { | x, y | x },
				SkoarpuscleChoard:  { | x, y | x },
				SkoarpuscleLies:    { | x, y | x },
				SkoarpuscleList:    { | x, y | x }
			),

			SkoarpuscleSymbol: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol ++ 'Hz') },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleSymbol(x.val ++ y.asSymbol) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleSymbol(x.val ++ y.asSymbol) },

				SkoarpuscleLies:    { | x, y | SkoarpuscleString(x.val ++ y.asSymbol) },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) },
			),

			SkoarpuscleString: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleString(x.val ++ y.val.asString ++ "Hz") },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleString(x.val ++ y.asString) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleString(x.val ++ y.asString) },

				SkoarpuscleLies:    { | x, y | SkoarpuscleString(x.val ++ y.asString) },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) },
			),

			SkoarpuscleFreq: (
				
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFreq(x.val + y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFreq(x.val + y.val) },			 
				SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFreq(x.val + y.val) },			 
				SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleList([x, y]) },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ 'Hz' ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val.asString ++ "Hz" ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }
				  
			)


        );

		// ---------------------
		// Multiplication Tables
		// ---------------------
		// x by y
        multiplication = (

			SkoarpuscleCrap: ( Any: { | x, y | SkoarpuscleCrap.new } ),

			// x
            SkoarpuscleInt: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleInt(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val * y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
				    
            ),

            // x
            SkoarpuscleFloat: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
            ),

			// x
            SkoarpuscleHashLevel: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
            ),

			SkoarpuscleList: (
				// y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleNoat: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleChoard: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleLies: (
				SkoarpuscleInt:         { | x, y | x },
                SkoarpuscleFloat:       { | x, y | x },
				SkoarpuscleHashLevel:   { | x, y | x },
				SkoarpuscleFreq:        { | x, y | x },
				SkoarpuscleNoat:        { | x, y | x },
				SkoarpuscleSymbol:      { | x, y | x },
				SkoarpuscleString:      { | x, y | x },
				SkoarpuscleChoard:      { | x, y | x },
				SkoarpuscleLies:        { | x, y | x },
                SkoarpuscleList:        { | x, y | x }
			),

			SkoarpuscleSymbol: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleString: (

				SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleFreq: (
				
              
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleFreq(x.val * y.val) },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:  { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleLies:    { | x, y | y },
				SkoarpuscleCrap:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			  
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
        "assign ".post; v.val.post; v.dump;
        ^minstrel.fairy.impress(f.(minstrel, v, settable));
    }

    // x + y
    add {
        | minstrel, x, y |
        var f = this.lookup(addition, x, y);
        //"add".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }

    // x by y
    multiply {
        | minstrel, x, y |
        var f = this.lookup(multiplication, x, y);
        //"multiply".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }

}