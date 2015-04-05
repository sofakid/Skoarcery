
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((

	math_add_a: [
		"1 + 2 ) 3 + 4 + 5 )",
		[(\degree:3),(\degree:12)]
	],

	math_add_b: [
		"1 + 2 ) 3 + 4 + 5 ) $ + 3 )", 
		[(\degree:3),(\degree:12),(\degree:15)]
	],

	math_add_c: [
		"0 + 0 ) 1 + 1 + 1 => @x )", 
		[(\degree:0),(\degree:3)]
	],

	math_mul_a: [
		"1 x 2 ) 3 x 4 x 5 )", 
		[(\degree:2),(\degree:60)]
	],

	math_mul_b: [
		"1 + 2 x 5 ) 3 + 4 x 5 ) $ x 2 + 1 )",
		[(\degree:15),(\degree:35),(\degree:71)]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleSymbol: [
		"@abc + @def => @x )", 
		[(x:\abcdef)]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleInt: [
		"@abc + 7 => @x )", 
		[(x:\abc7)]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleNoat: [
		"@abc + c# => @x )", 
		[(x: 'abcc#' )]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleChoard: [
		"@abc + A# => @x )", 
		[(x: 'abcA' )]
	],

	//op_add_SkoarpuscleSymbol_SkoarpuscleLies: [
	//	"@abc + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleSymbol_SkoarpuscleList: [
		"@abc + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleFreq: [
		"@abc + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleSymbol_SkoarpuscleCrap: [
	//	"@abc + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleSymbol_SkoarpuscleString: [
		"@abc + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleSymbol_SkoarpuscleFloat: [
		"@abc + 2.18 => @x )", 
		[(x: nil )]
	],


	op_add_SkoarpuscleInt_SkoarpuscleSymbol: [
		"7 + @abc => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleInt_SkoarpuscleInt: [
		"7 + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleInt_SkoarpuscleNoat: [
		"7 + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleInt_SkoarpuscleChoard: [
		"7 + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleInt_SkoarpuscleLies: [
	//	"7 + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleInt_SkoarpuscleList: [
		"7 + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleInt_SkoarpuscleFreq: [
		"7 + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleInt_SkoarpuscleCrap: [
	//	"7 + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleInt_SkoarpuscleString: [
		"7 + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleInt_SkoarpuscleFloat: [
		"7 + 2.18 => @x )", 
		[(x: nil )]
	],


	op_add_SkoarpuscleList_SkoarpuscleSymbol: [
		"<0,1> + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleList_SkoarpuscleInt: [
		"<0,1> + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleList_SkoarpuscleNoat: [
		"<0,1> + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleList_SkoarpuscleChoard: [
		"<0,1> + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleList_SkoarpuscleLies: [
	//	"<0,1> + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleList_SkoarpuscleList: [
		"<0,1> + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleList_SkoarpuscleFreq: [
		"<0,1> + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleList_SkoarpuscleCrap: [
	//	"<0,1> + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleList_SkoarpuscleString: [
		"<0,1> + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleList_SkoarpuscleFloat: [
		"<0,1> + 2.18 => @x )", 
		[(x: nil )]
	],


	op_add_SkoarpuscleNoat_SkoarpuscleSymbol: [
		"c# + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleNoat_SkoarpuscleInt: [
		"c# + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleNoat_SkoarpuscleNoat: [
		"c# + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleNoat_SkoarpuscleChoard: [
		"c# + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleNoat_SkoarpuscleLies: [
	//	"c# + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleNoat_SkoarpuscleList: [
		"c# + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleNoat_SkoarpuscleFreq: [
		"c# + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleNoat_SkoarpuscleCrap: [
	//	"c# + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleNoat_SkoarpuscleString: [
		"c# + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleNoat_SkoarpuscleFloat: [
		"c# + 2.18 => @x )", 
		[(x: nil )]
	],


	op_add_SkoarpuscleChoard_SkoarpuscleSymbol: [
		"A# + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleChoard_SkoarpuscleInt: [
		"A# + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleChoard_SkoarpuscleNoat: [
		"A# + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleChoard_SkoarpuscleChoard: [
		"A# + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleChoard_SkoarpuscleLies: [
	//	"A# + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleChoard_SkoarpuscleList: [
		"A# + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleChoard_SkoarpuscleFreq: [
		"A# + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleChoard_SkoarpuscleCrap: [
	//	"A# + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleChoard_SkoarpuscleString: [
		"A# + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleChoard_SkoarpuscleFloat: [
		"A# + 2.18 => @x )", 
		[(x: nil )]
	],


	/*op_add_SkoarpuscleLies_SkoarpuscleSymbol: [
		"lies + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleChoard: [
		"lies + A# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleFreq: [
		"lies + 2600Hz => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleList: [
		"lies + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleNoat: [
		"lies + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleString: [
		"lies + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleLies: [
		"lies + lies => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleInt: [
		"lies + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleLies_SkoarpuscleFloat: [
		"lies + 2.18 => @x )", 
		[(x: nil )]
	],
	*/

	op_add_SkoarpuscleString_SkoarpuscleSymbol: [
		"'ABC' + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleString_SkoarpuscleInt: [
		"'ABC' + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleString_SkoarpuscleNoat: [
		"'ABC' + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleString_SkoarpuscleChoard: [
		"'ABC' + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleString_SkoarpuscleLies: [
	//	"'ABC' + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleString_SkoarpuscleList: [
		"'ABC' + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleString_SkoarpuscleFreq: [
		"'ABC' + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleString_SkoarpuscleCrap: [
	//	"'ABC' + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleString_SkoarpuscleString: [
		"'ABC' + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleString_SkoarpuscleFloat: [
		"'ABC' + 2.18 => @x )", 
		[(x: nil )]
	],


	//op_add_SkoarpuscleCrap_Any: [
	//	"crap + Any => @x )", 
	//	[(x:nil)]
	//],


	op_add_SkoarpuscleFreq_SkoarpuscleSymbol: [
		"2600Hz + @abc=> @x )", 
		[(x:nil)]
	],

	op_add_SkoarpuscleFreq_SkoarpuscleInt: [
		"2600Hz + 7 => @x )", 
		[(x:2607)]
	],

	op_add_SkoarpuscleFreq_SkoarpuscleNoat: [
		"2600Hz + c# => @x )", 
		[(x:nil)]
	],

	op_add_SkoarpuscleFreq_SkoarpuscleChoard: [
		"2600Hz + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleFreq_SkoarpuscleLies: [
	//	"2600Hz + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleFreq_SkoarpuscleList: [
		"2600Hz + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFreq_SkoarpuscleFreq: [
		"2600Hz + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleFreq_SkoarpuscleCrap: [
	//	"2600Hz + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleFreq_SkoarpuscleString: [
		"2600Hz + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFreq_SkoarpuscleFloat: [
		"2600Hz + 2.18 => @x )", 
		[(x: nil )]
	],


	op_add_SkoarpuscleFloat_SkoarpuscleSymbol: [
		"2.18 + @abc=> @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFloat_SkoarpuscleInt: [
		"2.18 + 7 => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFloat_SkoarpuscleNoat: [
		"2.18 + c# => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFloat_SkoarpuscleChoard: [
		"2.18 + A# => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleFloat_SkoarpuscleLies: [
	//	"2.18 + lies => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleFloat_SkoarpuscleList: [
		"2.18 + <0,1> => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFloat_SkoarpuscleFreq: [
		"2.18 + 2600Hz => @x )", 
		[(x: nil )]
	],

	//op_add_SkoarpuscleFloat_SkoarpuscleCrap: [
	//	"2.18 + crap => @x )", 
	//	[(x: nil )]
	//],

	op_add_SkoarpuscleFloat_SkoarpuscleString: [
		"2.18 + 'ABC' => @x )", 
		[(x: nil )]
	],

	op_add_SkoarpuscleFloat_SkoarpuscleFloat: [
		"2.18 + 2.18 => @x )", 
		[(x: nil )]
	],


));
