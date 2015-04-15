
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

	
	// ---------------------------------------------------
	// ---------------------------------------------------
	//     Addition
	// ---------------------------------------------------
	// ---------------------------------------------------
	
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


	op_add_Symbol_List: [
		"@abc + <0,1> => @x )", 
		[(x: ['abc', 0, 1])]
	],

	op_add_Symbol_Freq: [
		"@abc + 2600Hz => @x )", 
		[(x: 'abc2600Hz')]
	],

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


	op_add_Int_List: [
		"7 + <0,1> => @x )", 
		[(x: [7,0,1])]
	],

	op_add_Int_Freq: [
		"7 + 2600Hz )", 
		[(freq: 2607)]
	],



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

	op_add_List_List: [
		"<0,1> + <0,1> => @x )", 
		[(x: [0,1,0,1])]
	],

	op_add_List_Freq: [
		"<0,1> + 2600Hz => @x )", 
		[(x: [0,1,2600])]
	],

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



	op_add_Noat_String: [
		"c# + 'ABC' => @x )", 
		[(x: "c#ABC")]
	],

	
	op_add_Choard_Symbol: [
		"A# + @abc=> @x )", 
		[(x: 'A#abc')]
	],

	

	op_add_Choard_String: [
		"A# + 'ABC' => @x )", 
		[(x: "A#ABC" )]
	],

	

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

	op_add_String_List: [
		"'ABC' + <0,1> => @x )", 
		[(x: ["ABC",0,1])]
	],

	op_add_String_Freq: [
		"'ABC' + 2600Hz => @x )", 
		[(x: "ABC2600Hz" )]
	],



	op_add_String_String: [
		"'ABC' + 'ABC' => @x )", 
		[(x: "ABCABC" )]
	],

	op_add_String_Float: [
		"'ABC' + 2.18 => @x )", 
		[(x: "ABC2.18" )]
	],

	op_add_Freq_Symbol: [
		"2600Hz + @abc=> @x )", 
		[(x:'2600Hzabc')]
	],

	op_add_Freq_Int: [
		"2600Hz + 7 => @x )", 
		[(x:2607)]
	],


	op_add_Freq_List: [
		"2600Hz + <0,1> => @x )", 
		[(x: [2600,0,1])]
	],

	op_add_Freq_Freq: [
		"2600Hz + 2600Hz => @x )", 
		[(x: 5200)]
	],

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

	// ---------------------------------------------------
	// ---------------------------------------------------
	//     Subtraction
	// ---------------------------------------------------
	// ---------------------------------------------------
	
	op_sub_Int_Int: [
		"7 - 7 => @x )", 
		[(x: 0)]
	],

	op_sub_Int_Freq: [
		"7 - 2600Hz => @x )", 
		[(x: -2593)] 
	],

	op_sub_Int_Float: [
		"7 - 2.18 => @x )", 
		[(x: 4.82)]
	],

	op_sub_Freq_Freq: [
		"2600Hz - 2600Hz => @x )", 
		[(x: 0)]
	],

	op_sub_Freq_Float: [
		"2600Hz - 2.18 )", 
		[(freq: 2597.82 )]
	],

	op_sub_Float_Int: [
		"2.18 - 7 => @x )", 
		[(x: -4.82)]
	],

	op_sub_Float_Float: [
		"2.18 - 2.18 => @x )", 
		[(x: 0)]
	],

	op_sub_Hash_Int: [
		"[###  ] - 7 => @x )", 
		[(x: -6.4)]
	],

	op_sub_Int_Hash: [
		"7 - [###  ]=> @x )", 
		[(x: 6.4)]
	],

	op_sub_Hash_Hash: [
		"[###       ] - [####      ] => @x )", 
		[(x: -0.1)]
	],


	op_add_Float_String: [
		"2.18 + 'ABC' => @x )", 
		[(x: "2.18ABC")]
	],

	// False
	op_add_Float_False: [
		"2.18 + no => @x )", 
		[(x: false)]
	],

	op_add_Symbol_False: [
		"@abc + no => @x )", 
		[(x: false)]
	],

	op_add_Int_False: [
		"7 + no => @x )", 
		[(x: false)]
	],

	op_add_List_False: [
		"<0,1> + no => @x )", 
		[(x: false)]
	],

	op_add_Noat_False: [
		"c# + no => @x )", 
		[(x: false )]
	],

	op_add_Choard_False: [
		"A# + no => @x )", 
		[(x: false)]
	],

	op_add_False_Symbol: [
		"no + @abc=> @x )", 
		[(x: false)]
	],

	op_add_False_Choard: [
		"no + A# => @x )", 
		[(x: false)]
	],

	op_add_False_Freq: [
		"no + 2600Hz => @x )", 
		[(x: false)]
	],

	op_add_False_List: [
		"no + <0,1> => @x )", 
		[(x: false)]
	],

	op_add_False_Noat: [
		"no + c# => @x )", 
		[(x: false)]
	],

	op_add_False_String: [
		"no + 'ABC' => @x )", 
		[(x: false)]
	],

	op_add_False_False: [
		"no + no => @x )", 
		[(x: false)]
	],

	op_add_False_Int: [
		"no + 7 => @x )", 
		[(x: false)]
	],

	op_add_False_Float: [
		"no + 2.18 => @x )", 
		[(x: false)]
	],
	
	op_add_String_False: [
		"'ABC' + no => @x )", 
		[(x: false)]
	],

	// True
	op_add_Float_True: [
		"2.18 + yes => @x )", 
		[(x: 2.18)]
	],

	op_add_Symbol_True: [
		"@abc + yes => @x )", 
		[(x: \abc)]
	],

	op_add_Int_True: [
		"7 + yes => @x )", 
		[(x: 7)]
	],

	op_add_List_True: [
		"<0,1> + yes => @x )", 
		[(x: [0,1])]
	],

	op_add_Noat_True: [
		"c# + yes => @x )", 
		[(x: nil )]
	],

	op_add_Choard_True: [
		"A# + yes => @x )", 
		[(x: nil)]
	],

	op_add_True_Symbol: [
		"yes + @abc=> @x )", 
		[(x: 'abc')]
	],

	op_add_True_Choard: [
		"yes + A# => @x )", 
		[(x: nil)]
	],

	op_add_True_Freq: [
		"yes + 2600Hz => @x )", 
		[(x: 2600)]
	],

	op_add_True_List: [
		"yes + <0,1> => @x )", 
		[(x: [0,1])]
	],

	op_add_True_Noat: [
		"yes + c# => @x )", 
		[(x: nil)]
	],

	op_add_True_String: [
		"yes + 'ABC' => @x )", 
		[(x: "ABC")]
	],

	op_add_True_True: [
		"yes + yes => @x )", 
		[(x: true)]
	],

	op_add_True_Int: [
		"yes + 7 => @x )", 
		[(x: 7)]
	],

	op_add_True_Float: [
		"yes + 2.18 => @x )", 
		[(x: 2.18)]
	],
	
	op_add_String_True: [
		"'ABC' + yes => @x )", 
		[(x: "ABC")]
	],

	// ---------------------------------------------------
	// ---------------------------------------------------
	//     Multiplication
	// ---------------------------------------------------
	// ---------------------------------------------------
	op_mul_Int_Int: [
		"7 x 7 => @x )", 
		[(x: 49)]
	],
	
	op_mul_Int_Freq: [
		"7 x 2600Hz )", 
		[(freq: 18200)]
	],
	
	op_mul_Int_Float: [
		"7 x 2.18 => @x )", 
		[(x: 15.26)]
	],
	
	op_mul_Freq_Int: [
		"2600Hz x 7 => @x )", 
		[(x:18200)]
	],

	op_mul_Freq_Freq: [
		"2600Hz x 2600Hz )", 
		[(freq:6760000)]
	],
	
	op_mul_Freq_Float: [
		"2600Hz x 2.18 => @x )", 
		[(x: 5668 )]
	],

	op_mul_Float_Int: [
		"2.18 x 7 => @x )", 
		[(x: 15.26)]
	],

	op_mul_Float_Float: [
		"2.18 x 2.18 => @x )", 
		[(x: 4.7524)]
	],

	op_mul_Hash_Int: [
		"[###  ] x 7 => @x )", 
		[(x: 4.2)]
	],

	op_mul_Int_Hash: [
		"7 x [###  ]=> @x )", 
		[(x: 4.2)]
	],

	op_mul_Hash_Hash: [
		"[###       ] x [####      ] => @x )", 
		[(x: 0.12)]
	],
));
