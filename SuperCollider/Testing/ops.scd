
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

	op_add_Symbol_Symbol: [
		"@abc + @def => @x )", 
		[(x:'abcdef')]
	],

	op_add_Symbol_Int: [
		"@abc + 7 => @x )", 
		[(x:'abc7')]
	],

	op_add_Symbol_Noat: [
		"@abc + c# => @x )", 
		[(x: 'abcc#')]
	],

	op_add_Symbol_Choard: [
		"@abc + A# => @x )", 
		[(x: 'abcA#')]
	],

	//op_add_Symbol_Lies: [
	//	"@abc + lies => @x )", 
	//	[(x: false)]
	//],

	op_add_Symbol_List: [
		"@abc + <0,1> => @x )", 
		[(x: ['abc', 0, 1])]
	],

	op_add_Symbol_Freq: [
		"@abc + 2600Hz => @x )", 
		[(x: 'abc2600Hz')]
	],

	//op_add_Symbol_Crap: [
	//	"@abc + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Symbol_String: [
		"@abc + 'ABC' => @x )", 
		[(x: 'abcABC')]
	],

	op_add_Symbol_Float: [
		"@abc + 2.18 => @x )", 
		[(x: 'abc2.18')]
	],

	op_add_Int_Symbol: [
		"7 + @abc => @x )", 
		[(x: '7abc')]
	],

	op_add_Int_Int: [
		"7 + 7 => @x )", 
		[(x: 14)]
	],

	op_add_Int_Noat: [
		"7 + c# => @x )", 
		[(x: inf)]
	],

	op_add_Int_Choard: [
		"7 + A# => @x )", 
		[(x: inf)]
	],

	//op_add_Int_Lies: [
	//	"7 + lies => @x )", 
	//	[(x: inf)]
	//],

	op_add_Int_List: [
		"7 + <0,1> => @x )", 
		[(x: [7,0,1])]
	],

	op_add_Int_Freq: [
		"7 + 2600Hz )", 
		[(freq: 2607)] // how do i test the type?
	],

	//op_add_Int_Crap: [
	//	"7 + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Int_String: [
		"7 + 'ABC' => @x )", 
		[(x: "7ABC")]
	],

	op_add_Int_Float: [
		"7 + 2.18 => @x )", 
		[(x: 9.18)]
	],


	op_add_List_Symbol: [
		"<0,1> + @abc => @x )", 
		[(x: [0,1,\abc])]
	],

	op_add_List_Int: [
		"<0,1> + 7 => @x )", 
		[(x: [0,1,7])]
	],

	op_add_List_Noat: [
		"<0,1> + c# => @x )", 
		[(x: inf)]
	],

	op_add_List_Choard: [
		"<0,1> + A# => @x )", 
		[(x: inf)]
	],

	//op_add_List_Lies: [
	//	"<0,1> + lies => @x )", 
	//	[(x: inf)]
	//],

	op_add_List_List: [
		"<0,1> + <0,1> => @x )", 
		[(x: [0,1,0,1])]
	],

	op_add_List_Freq: [
		"<0,1> + 2600Hz => @x )", 
		[(x: [0,1,2600])]
	],

	//op_add_List_Crap: [
	//	"<0,1> + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_List_String: [
		"<0,1> + 'ABC' => @x )", 
		[(x: [0,1,"ABC"] )]
	],

	op_add_List_Float: [
		"<0,1> + 2.18 => @x )", 
		[(x: [0,1,2.18])]
	],


	op_add_Noat_Symbol: [
		"c# + @abc=> @x )", 
		[(x: 'c#abc')]
	],

	op_add_Noat_Int: [
		"c# + 7 => @x )", 
		[(x: inf)]
	],

	op_add_Noat_Noat: [
		"c# + c# => @x )", 
		[(x: inf )]
	],

	op_add_Noat_Choard: [
		"c# + A# => @x )", 
		[(x: inf )]
	],

	//op_add_Noat_Lies: [
	//	"c# + lies => @x )", 
	//	[(x: inf )]
	//],

	op_add_Noat_List: [
		"c# + <0,1> => @x )", 
		[(x: inf )]
	],

	op_add_Noat_Freq: [
		"c# + 2600Hz => @x )", 
		[(x: inf )]
	],

	//op_add_Noat_Crap: [
	//	"c# + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Noat_String: [
		"c# + 'ABC' => @x )", 
		[(x: "c#ABC")]
	],

	op_add_Noat_Float: [
		"c# + 2.18 => @x )", 
		[(x: inf)]
	],


	op_add_Choard_Symbol: [
		"A# + @abc=> @x )", 
		[(x: 'A#abc')]
	],

	op_add_Choard_Int: [
		"A# + 7 => @x )", 
		[(x: inf)]
	],

	op_add_Choard_Noat: [
		"A# + c# => @x )", 
		[(x: inf)]
	],

	op_add_Choard_Choard: [
		"A# + A# => @x )", 
		[(x: inf)]
	],

	//op_add_Choard_Lies: [
	//	"A# + lies => @x )", 
	//	[(x: inf)]
	//],

	op_add_Choard_List: [
		"A# + <0,1> => @x )", 
		[(x: inf)]
	],

	op_add_Choard_Freq: [
		"A# + 2600Hz => @x )", 
		[(x: inf)]
	],

	//op_add_Choard_Crap: [
	//	"A# + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Choard_String: [
		"A# + 'ABC' => @x )", 
		[(x: "A#ABC" )]
	],

	op_add_Choard_Float: [
		"A# + 2.18 => @x )", 
		[(x: inf)]
	],


	/*op_add_Lies_Symbol: [
		"lies + @abc=> @x )", 
		[(x: inf)]
	],

	op_add_Lies_Choard: [
		"lies + A# => @x )", 
		[(x: inf)]
	],

	op_add_Lies_Freq: [
		"lies + 2600Hz => @x )", 
		[(x: inf)]
	],

	op_add_Lies_List: [
		"lies + <0,1> => @x )", 
		[(x: inf)]
	],

	op_add_Lies_Noat: [
		"lies + c# => @x )", 
		[(x: inf)]
	],

	op_add_Lies_String: [
		"lies + 'ABC' => @x )", 
		[(x: inf)]
	],

	op_add_Lies_Lies: [
		"lies + lies => @x )", 
		[(x: inf)]
	],

	op_add_Lies_Int: [
		"lies + 7 => @x )", 
		[(x: inf)]
	],

	op_add_Lies_Float: [
		"lies + 2.18 => @x )", 
		[(x: inf)]
	],
	*/

	op_add_String_Symbol: [
		"'ABC' + @abc => @x )", 
		[(x: "ABCabc" )]
	],

	op_add_String_Int: [
		"'ABC' + 7 => @x )", 
		[(x: "ABC7" )]
	],

	op_add_String_Noat: [
		"'ABC' + c# => @x )", 
		[(x: "ABCc#" )]
	],

	op_add_String_Choard: [
		"'ABC' + A# => @x )", 
		[(x: "ABCA#")]
	],

	//op_add_String_Lies: [
	//	"'ABC' + lies => @x )", 
	//	[(x: false )]
	//],

	op_add_String_List: [
		"'ABC' + <0,1> => @x )", 
		[(x: ["ABC",0,1])]
	],

	op_add_String_Freq: [
		"'ABC' + 2600Hz => @x )", 
		[(x: "ABC2600Hz" )]
	],

	//op_add_String_Crap: [
	//	"'ABC' + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_String_String: [
		"'ABC' + 'ABC' => @x )", 
		[(x: "ABCABC" )]
	],

	op_add_String_Float: [
		"'ABC' + 2.18 => @x )", 
		[(x: "ABC2.18" )]
	],


	//op_add_Crap_Any: [
	//	"crap + Any => @x )", 
	//	[(x:nil)]
	//],


	op_add_Freq_Symbol: [
		"2600Hz + @abc=> @x )", 
		[(x:'2600Hzabc')]
	],

	op_add_Freq_Int: [
		"2600Hz + 7 => @x )", 
		[(x:2607)]
	],

	op_add_Freq_Noat: [
		"2600Hz + c# => @x )", 
		[(x:inf)]
	],

	op_add_Freq_Choard: [
		"2600Hz + A# => @x )", 
		[(x: inf )]
	],

	//op_add_Freq_Lies: [
	//	"2600Hz + lies => @x )", 
	//	[(x: inf)]
	//],

	op_add_Freq_List: [
		"2600Hz + <0,1> => @x )", 
		[(x: [2600,0,1])]
	],

	op_add_Freq_Freq: [
		"2600Hz + 2600Hz => @x )", 
		[(x: 5200)]
	],

	//op_add_Freq_Crap: [
	//	"2600Hz + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Freq_String: [
		"2600Hz + 'ABC' => @x )", 
		[(x: "2600HzABC" )]
	],

	op_add_Freq_Float: [
		"2600Hz + 2.18 => @x )", 
		[(x: 2602.18 )]
	],


	op_add_Float_Symbol: [
		"2.18 + @abc=> @x )", 
		[(x: '2.18abc')]
	],

	op_add_Float_Int: [
		"2.18 + 7 => @x )", 
		[(x: 9.18)]
	],

	op_add_Float_Noat: [
		"2.18 + c# => @x )", 
		[(x: inf)]
	],

	op_add_Float_Choard: [
		"2.18 + A# => @x )", 
		[(x: inf)]
	],

	//op_add_Float_Lies: [
	//	"2.18 + lies => @x )", 
	//	[(x: inf)]
	//],

	op_add_Float_List: [
		"2.18 + <0,1> => @x )", 
		[(x: inf)]
	],

	op_add_Float_Freq: [
		"2.18 + 2600Hz => @x )", 
		[(x: inf)]
	],

	//op_add_Float_Crap: [
	//	"2.18 + crap => @x )", 
	//	[(x: inf)]
	//],

	op_add_Float_String: [
		"2.18 + 'ABC' => @x )", 
		[(x: inf)]
	],

	op_add_Float_Float: [
		"2.18 + 2.18 => @x )", 
		[(x: 4.36)]
	],

	op_add_Hash_Int: [
		"[###  ] + 7 => @x )", 
		[(x: 7.6)]
	],

	op_add_Int_Hash: [
		"7 + [###  ]=> @x )", 
		[(x: 7.6)]
	],

	op_add_Hash_Hash: [
		"[###       ] + [####      ] => @x )", 
		[(x: 0.7)]
	],


));
