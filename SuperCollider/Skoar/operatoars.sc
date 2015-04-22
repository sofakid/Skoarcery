SkoarOps {

    var <assignment;

    var <addition;
	var <subtraction;
    var <multiplication;
    var <division;

    *new { ^super.new.init; }

    init {

		this.init_assignments;
		this.init_addition;
		this.init_subtraction;
		this.init_multiplication;
	}

	// -----------------
	// Assignment Tables
	// -----------------
	init_assignments {
	
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
	}

	// ---------------
	// Addition Tables
	// ---------------
	init_addition {
	
		// x + y
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

				SkoarpuscleFalse:    { | x, y | y },
				SkoarpuscleTrue:    { | x, y | x },

				SkoarpuscleSymbol:  { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:  { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },

                SkoarpuscleList:    { | x, y | SkoarpuscleList([x] ++ y.val) }

            ),

            // x +
            SkoarpuscleFloat: (
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFloat(x.val + y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleCrap:      { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) }
            ),

			// x +
            SkoarpuscleHashLevel: (
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFloat(x.val + y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFloat(x.val + y.val) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.val.asString ++ y.val) },
                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) }
            ),

			SkoarpuscleList: (
				// y
                SkoarpuscleInt:       { | x, y | SkoarpuscleList(x.val ++ [y]) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleList(x.val ++ [y]) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList(x.val ++ [y]) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleCrap:      { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleList(x.val ++ [y]) },
				SkoarpuscleString:    { | x, y | SkoarpuscleList(x.val ++ [y]) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList(x.val ++ y.val)}
			),

			SkoarpuscleNoat: (
				SkoarpuscleInt:       { | x, y | x.raiseBy(y) },
                SkoarpuscleFloat:     { | x, y | x.raiseBy(y) },
				SkoarpuscleHashLevel: { | x, y | x.raiseBy(y) },
				SkoarpuscleFreq:      { | x, y | x.raiseBy(y) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.asSymbol ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.asString ++ y.val) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) }
			),

			SkoarpuscleChoard: (
				SkoarpuscleInt:       { | x, y | x.raiseBy(y) },
                SkoarpuscleFloat:     { | x, y | x.raiseBy(y) },
				SkoarpuscleHashLevel: { | x, y | x.raiseBy(y) },
				SkoarpuscleFreq:      { | x, y | x.raiseBy(y) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList([x, y]) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.asSymbol ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.asString ++ y.val) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) }
			),

			SkoarpuscleFalse: (
				SkoarpuscleInt:       { | x, y | x },
                SkoarpuscleFloat:     { | x, y | x },
				SkoarpuscleHashLevel: { | x, y | x },
				SkoarpuscleFreq:      { | x, y | x },
				SkoarpuscleNoat:      { | x, y | x },
				SkoarpuscleSymbol:    { | x, y | x },
				SkoarpuscleString:    { | x, y | x },
				SkoarpuscleChoard:    { | x, y | x },
				SkoarpuscleFalse:     { | x, y | x },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleList:      { | x, y | x }
			),

			SkoarpuscleTrue: (
				SkoarpuscleInt:       { | x, y | y },
                SkoarpuscleFloat:     { | x, y | y },
				SkoarpuscleHashLevel: { | x, y | y },
				SkoarpuscleFreq:      { | x, y | y },
				SkoarpuscleNoat:      { | x, y | y },
				SkoarpuscleSymbol:    { | x, y | y },
				SkoarpuscleString:    { | x, y | y },
				SkoarpuscleChoard:    { | x, y | y },
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | y },
				SkoarpuscleList:      { | x, y | y }
			),

			SkoarpuscleSymbol: (
				SkoarpuscleInt:       { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol ++ 'Hz') },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleSymbol(x.val ++ y.asSymbol) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleSymbol(x.val ++ y.asSymbol) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleCrap:      { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.val ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleSymbol(x.val ++ y.val.asSymbol) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) },
			),

			SkoarpuscleString: (
				SkoarpuscleInt:       { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleString(x.val ++ y.val.asString ++ "Hz") },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleString(x.val ++ y.asString) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleString(x.val ++ y.asString) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleString(x.val ++ y.val.asString) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.val ++ y.val) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) },
			),

			SkoarpuscleFreq: (
				
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFreq(x.val + y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFreq(x.val + y.val) },			 
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFreq(x.val + y.val) },			 
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val + y.val) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleList([x, y]) },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleList([x, y]) },
				
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },

				SkoarpuscleSymbol:    { | x, y | SkoarpuscleSymbol(x.val.asSymbol ++ 'Hz' ++ y.val) },
				SkoarpuscleString:    { | x, y | SkoarpuscleString(x.val.asString ++ "Hz" ++ y.val) },

                SkoarpuscleList:      { | x, y | SkoarpuscleList([x] ++ y.val) }
				  
			)

        );
	}
	
	// ------------------
	// Subtraction Tables
	// ------------------
	init_subtraction {
	
		// x - y
        subtraction = (

			SkoarpuscleCrap: ( Any: { | x, y | SkoarpuscleCrap.new } ),

            // x -
            SkoarpuscleInt: (
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleInt(x.val - y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val - y.val) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
            ),

            // x -
            SkoarpuscleFloat: (
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFloat(x.val - y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val - y.val) },
			
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
            ),

			// x -
            SkoarpuscleHashLevel: (
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFloat(x.val - y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleHashLevel: { | x, y | SkoarpuscleFloat(x.val - y.val) },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val - y.val) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
            ),

			
			SkoarpuscleNoat: (
				SkoarpuscleInt:       { | x, y | x.raiseBy(y) },
                SkoarpuscleFloat:     { | x, y | x.raiseBy(y) },
				SkoarpuscleHashLevel: { | x, y | x.raiseBy(y) },
				SkoarpuscleFreq:      { | x, y | x.raiseBy(y) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
			),

			SkoarpuscleChoard: (
				SkoarpuscleInt:       { | x, y | x.lowerBy(y) },
                SkoarpuscleFloat:     { | x, y | x.lowerBy(y) },
				SkoarpuscleHashLevel: { | x, y | x.lowerBy(y) },
				SkoarpuscleFreq:      { | x, y | x.lowerBy(y) },

				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
			),

			SkoarpuscleFalse: (
				SkoarpuscleInt:       { | x, y | x },
                SkoarpuscleFloat:     { | x, y | x },
				SkoarpuscleHashLevel: { | x, y | x },
				SkoarpuscleFreq:      { | x, y | x },
				SkoarpuscleNoat:      { | x, y | x },
				SkoarpuscleSymbol:    { | x, y | x },
				SkoarpuscleString:    { | x, y | x },
				SkoarpuscleChoard:    { | x, y | x },
				SkoarpuscleFalse:     { | x, y | x },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleList:      { | x, y | x }
			),

			SkoarpuscleTrue: (
				SkoarpuscleInt:       { | x, y | y },
                SkoarpuscleFloat:     { | x, y | y },
				SkoarpuscleHashLevel: { | x, y | y },
				SkoarpuscleFreq:      { | x, y | y },
				SkoarpuscleNoat:      { | x, y | y },
				SkoarpuscleSymbol:    { | x, y | y },
				SkoarpuscleString:    { | x, y | y },
				SkoarpuscleChoard:    { | x, y | y },
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | y },
				SkoarpuscleList:      { | x, y | y }
			),

			SkoarpuscleFreq: (
				
                // y
                SkoarpuscleInt:        { | x, y | SkoarpuscleFreq(x.val - y.val) },
                SkoarpuscleFloat:      { | x, y | SkoarpuscleFreq(x.val - y.val) },			 
				SkoarpuscleHashLevel:  { | x, y | SkoarpuscleFreq(x.val - y.val) },			 
				SkoarpuscleFreq:       { | x, y | SkoarpuscleFreq(x.val - y.val) },

				SkoarpuscleFalse:   { | x, y | y },
				SkoarpuscleTrue:    { | x, y | x },
				
			)

        );
	}

	// ---------------------
	// Multiplication Tables
	// ---------------------
	init_multiplication {

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
			
				SkoarpuscleFalse:   { | x, y | y },
				SkoarpuscleTrue:    { | x, y | x },
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
				
				SkoarpuscleFalse:    { | x, y | y },
				SkoarpuscleTrue:     { | x, y | x },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
            ),

			// x
            SkoarpuscleHashLevel: (
                // y
                SkoarpuscleInt:     { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleFloat(x.val * y.val) },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleFalse:   { | x, y | y },
				SkoarpuscleTrue:    { | x, y | x },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
            ),

			SkoarpuscleList: (
				// y
                SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleNoat:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:  { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleFalse:    { | x, y | y },
				SkoarpuscleTrue:     { | x, y | x },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleNoat: (
				SkoarpuscleInt:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel:   { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFreq:    { | x, y | SkoarpuscleCrap.new },

				SkoarpuscleFalse:    { | x, y | y },
				SkoarpuscleTrue:    { | x, y | x },
				
				SkoarpuscleList:    { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleChoard: (
				SkoarpuscleInt:       { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleCrap.new },
                SkoarpuscleHashLevel: { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleFreq:      { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleList:      { | x, y | SkoarpuscleCrap.new }
			),

			SkoarpuscleFalse: (
				SkoarpuscleInt:         { | x, y | x },
                SkoarpuscleFloat:       { | x, y | x },
				SkoarpuscleHashLevel:   { | x, y | x },
				SkoarpuscleFreq:        { | x, y | x },
				SkoarpuscleNoat:        { | x, y | x },
				SkoarpuscleSymbol:      { | x, y | x },
				SkoarpuscleString:      { | x, y | x },
				SkoarpuscleChoard:      { | x, y | x },
				SkoarpuscleFalse:       { | x, y | x },
                SkoarpuscleTrue:        { | x, y | x },
				SkoarpuscleList:        { | x, y | x }
			),

			SkoarpuscleTrue: (
				SkoarpuscleInt:        { | x, y | y },
                SkoarpuscleFloat:      { | x, y | y },
				SkoarpuscleHashLevel:  { | x, y | y },
				SkoarpuscleFreq:       { | x, y | y },
				SkoarpuscleNoat:       { | x, y | y },
				SkoarpuscleSymbol:     { | x, y | y },
				SkoarpuscleString:     { | x, y | y },
				SkoarpuscleChoard:     { | x, y | y },
				SkoarpuscleFalse:      { | x, y | y },
				SkoarpuscleTrue:       { | x, y | y },
				SkoarpuscleList:       { | x, y | y }
			),

			SkoarpuscleFreq: (
              
                // y
                SkoarpuscleInt:       { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleFloat:     { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleHashLevel: { | x, y | SkoarpuscleFreq(x.val * y.val) },
                SkoarpuscleFreq:      { | x, y | SkoarpuscleFreq(x.val * y.val) },

				SkoarpuscleNoat:      { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleChoard:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleSymbol:    { | x, y | SkoarpuscleCrap.new },
				SkoarpuscleString:    { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleFalse:     { | x, y | y },
				SkoarpuscleTrue:      { | x, y | x },
				SkoarpuscleCrap:      { | x, y | SkoarpuscleCrap.new },
				
				SkoarpuscleList:      { | x, y | SkoarpuscleCrap.new }
			  
			)
		);
    }

    lookup {
        | op, x, y |
		var f;
        var table = op[x.class.asSymbol];
        
		if (table.isNil) {
			^{ | x, y | SkoarpuscleCrap.new };
		};
		
		f = table[y.class.asSymbol];

        if (f.isNil) {
            f = table[\Any];
        };

        if (f.notNil) {
            ^f
        };

		^{ | x, y | SkoarpuscleCrap.new };
        //SkoarError("Lookup fail.").throw;
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

	// x - y
    sub {
        | minstrel, x, y |
        var f = this.lookup(subtraction, x, y);
        //"sub".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }

    // x by y
    multiply {
        | minstrel, x, y |
        var f = this.lookup(multiplication, x, y);
        //"multiply".postln;
        ^minstrel.fairy.impress(f.(x, y));
    }

	// x +> y
    increment {
        | minstrel, x, y |
        var f;
		
		f = this.lookup(addition, y, x);
        //"increment".postln;
		^minstrel.fairy.impress(f.(x, y));
	}

	// x -> y
    decrement {
        | minstrel, x, y |
        var f = this.lookup(subtraction, y, x);
        //"decrement".postln;
		^minstrel.fairy.impress(f.(x, y));
	}
	 
	// x x> y
	multr {
        | minstrel, x, y |
        var f = this.lookup(multiplication, y, x);
        //"multr".postln;
		^minstrel.fairy.impress(f.(x, y));
	}
}