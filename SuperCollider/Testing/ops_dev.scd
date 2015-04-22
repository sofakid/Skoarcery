
var qrt = (\dur:1);
var eth = (\dur:1/2);

SkoarTestRunner((

	// ---------------------------------------------------
	// ---------------------------------------------------
	//     Addition
	// ---------------------------------------------------
	// ---------------------------------------------------
	
	





	op_add_Symbol_Crap: [
		"@abc + crap => @x )", 
		[(x: inf)]
	],

	op_add_Int_Noat: [
		"7 + c# => @x )", 
		[(x: inf)]
	],

	op_add_Int_Choard: [
		"7 + A# => @x )", 
		[(x: inf)]
	],

	

	op_add_Int_Crap: [
		"7 + crap => @x )", 
		[(x: inf)]
	],

	
	op_add_List_Noat: [
		"<0,1> + c# => @x )", 
		[(x: inf)]
	],

	op_add_List_Choard: [
		"<0,1> + A# => @x )", 
		[(x: inf)]
	],

	

	

	op_add_List_Crap: [
		"<0,1> + crap => @x )", 
		[(x: inf)]
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

	
	op_add_Noat_List: [
		"c# + <0,1> => @x )", 
		[(x: inf )]
	],

	op_add_Noat_Freq: [
		"c# + 2600Hz => @x )", 
		[(x: inf )]
	],

	op_add_Noat_Crap: [
		"c# + crap => @x )", 
		[(x: inf)]
	],

	

	op_add_Noat_Float: [
		"c# + 2.18 => @x )", 
		[(x: inf)]
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

	

	op_add_Choard_Float: [
		"A# + 2.18 => @x )", 
		[(x: inf)]
	],

	

	

	//op_add_String_Crap: [
	//	"'ABC' + crap => @x )", 
	//	[(x: inf)]
	//],

	

	//op_add_Crap_Any: [
	//	"crap + Any => @x )", 
	//	[(x:nil)]
	//],

	op_add_Freq_Noat: [
		"2600Hz + c# => @x )", 
		[(x:inf)]
	],

	op_add_Freq_Choard: [
		"2600Hz + A# => @x )", 
		[(x: inf )]
	],

	//op_add_Freq_False: [
	//	"2600Hz + false => @x )", 
	//	[(x: inf)]
	//],

	

	//op_add_Freq_Crap: [
	//	"2600Hz + crap => @x )", 
	//	[(x: inf)]
	//],

	

	

	op_add_Float_Noat: [
		"2.18 + c# => @x )", 
		[(x: inf)]
	],

	op_add_Float_Choard: [
		"2.18 + A# => @x )", 
		[(x: inf)]
	],



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

	

	// ---------------------------------------------------
	// ---------------------------------------------------
	//     Subtraction
	// ---------------------------------------------------
	// ---------------------------------------------------
	
	
	op_sub_Int_Noat: [
		"7 - c# => @x )", 
		[(x: inf)]
	],

	op_sub_Int_Choard: [
		"7 - A# => @x )", 
		[(x: inf)]
	],

	op_sub_List_Int: [
		"<0,1> - 7 => @x )", 
		[(x: inf)]
	],

	op_sub_List_Noat: [
		"<0,1> - c# => @x )", 
		[(x: inf)]
	],

	op_sub_List_Choard: [
		"<0,1> - A# => @x )", 
		[(x: inf)]
	],
	 

	op_sub_List_List: [
		"<0,1> - <0,1> => @x )", 
		[(x: inf)]
	],

	op_sub_List_Freq: [
		"<0,1> - 2600Hz => @x )", 
		[(x: inf)]
	],

	//op_sub_List_Crap: [
	//	"<0,1> - crap => @x )", 
	//	[(x: inf)]
	//],

	
	//op_sub_List_False: [
	//	"<0,1> - false => @x )", 
	//	[(x: inf)]
	//],
	op_sub_List_String: [
		"<0,1> - 'ABC' => @x )", 
		[(x: inf )]
	],

	op_sub_List_Float: [
		"<0,1> - 2.18 => @x )", 
		[(x: inf)]
	],


	op_sub_Noat_Symbol: [
		"c# - @abc=> @x )", 
		[(x: inf)]
	],

	op_sub_Noat_Int: [
		"c# - 7 => @x )", 
		[(x: inf)]
	],

	op_sub_Noat_Noat: [
		"c# - c# => @x )", 
		[(x: inf )]
	],

	op_sub_Noat_Choard: [
		"c# - A# => @x )", 
		[(x: inf )]
	],

	//op_sub_Noat_False: [
	//	"c# - false => @x )", 
	//	[(x: inf )]
	//],

	op_sub_Noat_List: [
		"c# - <0,1> => @x )", 
		[(x: inf )]
	],

	op_sub_Noat_Freq: [
		"c# - 2600Hz => @x )", 
		[(x: inf )]
	],

	//op_sub_Noat_Crap: [
	//	"c# - crap => @x )", 
	//	[(x: inf)]
	//],

	op_sub_Noat_Float: [
		"c# - 2.18 => @x )", 
		[(x: inf)]
	],

	op_sub_Choard_Int: [
		"A# - 7 => @x )", 
		[(x: inf)]
	],

	op_sub_Choard_Noat: [
		"A# - c# => @x )", 
		[(x: inf)]
	],

	op_sub_Choard_Choard: [
		"A# - A# => @x )", 
		[(x: inf)]
	],

	//op_sub_Choard_False: [
	//	"A# - false => @x )", 
	//	[(x: inf)]
	//],

	op_sub_Choard_List: [
		"A# - <0,1> => @x )", 
		[(x: inf)]
	],

	op_sub_Choard_Freq: [
		"A# - 2600Hz => @x )", 
		[(x: inf)]
	],

	//op_sub_Choard_Crap: [
	//	"A# - crap => @x )", 
	//	[(x: inf)]
	//],


	op_sub_Choard_Float: [
		"A# - 2.18 => @x )", 
		[(x: inf)]
	],


	op_sub_False_Symbol: [
		"false - @abc=> @x )", 
		[(x: inf)]
	],

	op_sub_False_Choard: [
		"false - A# => @x )", 
		[(x: inf)]
	],

	op_sub_False_Freq: [
		"false - 2600Hz => @x )", 
		[(x: inf)]
	],

	op_sub_False_List: [
		"false - <0,1> => @x )", 
		[(x: inf)]
	],

	op_sub_False_Noat: [
		"false - c# => @x )", 
		[(x: inf)]
	],

	op_sub_False_False: [
		"false - false => @x )", 
		[(x: inf)]
	],

	op_sub_False_Int: [
		"false - 7 => @x )", 
		[(x: inf)]
	],

	op_sub_False_Float: [
		"false - 2.18 => @x )", 
		[(x: inf)]
	],
	
	op_sub_Freq_Int: [
		"2600Hz - 7 => @x )", 
		[(x:2593)]
	],

	op_sub_Freq_Noat: [
		"2600Hz - c# => @x )", 
		[(x:inf)]
	],

	op_sub_Freq_Choard: [
		"2600Hz - A# => @x )", 
		[(x: inf )]
	],

	//op_sub_Freq_False: [
	//	"2600Hz - false => @x )", 
	//	[(x: inf)]
	//],

	op_sub_Freq_Freq: [
		"2600Hz - 2600Hz => @x )", 
		[(x: 0)]
	],

	//op_sub_Freq_Crap: [
	//	"2600Hz - crap => @x )", 
	//	[(x: inf)]
	//],

	
	op_sub_Freq_Float: [
		"2600Hz - 2.18 )", 
		[(freq: 2597.82 )]
	],

	op_sub_Float_Int: [
		"2.18 - 7 => @x )", 
		[(x: -4.82)]
	],

	op_sub_Float_Noat: [
		"2.18 - c# => @x )", 
		[(x: inf)]
	],

	op_sub_Float_Choard: [
		"2.18 - A# => @x )", 
		[(x: inf)]
	],

	op_sub_Float_False: [
		"2.18 - false => @x )", 
		[(x: inf)]
	],

	op_sub_Float_List: [
		"2.18 - <0,1> => @x )", 
		[(x: inf)]
	],

	op_sub_Float_Freq: [
		"2.18 - 2600Hz => @x )", 
		[(x: inf)]
	],

	op_sub_Float_Crap: [
		"2.18 - crap => @x )", 
		[(x: inf)]
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
		[(x: -0.9)]
	],

	
	op_add_Freq_Noat: [
		"2600Hz + c# => @x )", 
		[(x:inf)]
	],

	op_add_Freq_Choard: [
		"2600Hz + A# => @x )", 
		[(x: inf )]
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

	op_mul_Int_Noat: [
		"7 x c# => @x )", 
		[(x: inf)]
	],

	op_mul_Int_Choard: [
		"7 x A# => @x )", 
		[(x: inf)]
	],

	
	op_mul_Int_Freq: [
		"7 x 2600Hz )", 
		[(freq: 18200)]
	],

	
	op_mul_Int_Float: [
		"7 x 2.18 => @x )", 
		[(x: 15.26)]
	],


	op_mul_List_Int: [
		"<0,1> x 7 => @x )", 
		[(x: [0,7])]
	],

	op_mul_List_Noat: [
		"<0,1> x c# => @x )", 
		[(x: inf)]
	],

	op_mul_List_Choard: [
		"<0,1> x A# => @x )", 
		[(x: inf)]
	],

	op_mul_List_False: [
		"<0,1> x false => @x )", 
		[(x: inf)]
	],

	op_mul_List_List: [
		"<0,1> x <0,1> => @x )", 
		[(x: [[0,0],[0,1],[1,0],[1,1]])]
	],

	op_mul_List_Freq: [
		"<0,1> x 2600Hz => @x )", 
		[(x: [0,2600])]
	],

	op_mul_List_Crap: [
		"<0,1> x crap => @x )", 
		[(x: inf)]
	],

	op_mul_List_String: [
		"<0,1> x 'ABC' => @x )", 
		[(x: inf )]
	],

	op_mul_List_Float: [
		"<0,1> x 2.18 => @x )", 
		[(x: [0,2.18])]
	],


	op_mul_Noat_Symbol: [
		"c# x @abc=> @x )", 
		[(x: inf)]
	],

	op_mul_Noat_Int: [
		"c# x 7 => @x )", 
		[(x: inf)]
	],

	op_mul_Noat_Noat: [
		"c# x c# => @x )", 
		[(x: inf )]
	],

	op_mul_Noat_Choard: [
		"c# x A# => @x )", 
		[(x: inf )]
	],

	op_mul_Noat_False: [
		"c# x false => @x )", 
		[(x: inf )]
	],

	op_mul_Noat_List: [
		"c# x <0,1> => @x )", 
		[(x: inf )]
	],

	op_mul_Noat_Freq: [
		"c# x 2600Hz => @x )", 
		[(x: inf )]
	],

	op_mul_Noat_Crap: [
		"c# x crap => @x )", 
		[(x: inf)]
	],

	op_mul_Noat_Float: [
		"c# x 2.18 => @x )", 
		[(x: inf)]
	],



	op_mul_Choard_Int: [
		"A# x 7 => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_Noat: [
		"A# x c# => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_Choard: [
		"A# x A# => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_False: [
		"A# x false => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_List: [
		"A# x <0,1> => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_Freq: [
		"A# x 2600Hz => @x )", 
		[(x: inf)]
	],

	op_mul_Choard_Crap: [
		"A# x crap => @x )", 
		[(x: inf)]
	],


	op_mul_Choard_Float: [
		"A# x 2.18 => @x )", 
		[(x: inf)]
	],


	op_mul_False_Symbol: [
		"false x @abc=> @x )", 
		[(x: inf)]
	],

	op_mul_False_Choard: [
		"false x A# => @x )", 
		[(x: inf)]
	],

	op_mul_False_Freq: [
		"false x 2600Hz => @x )", 
		[(x: inf)]
	],

	op_mul_False_List: [
		"false x <0,1> => @x )", 
		[(x: inf)]
	],

	op_mul_False_Noat: [
		"false x c# => @x )", 
		[(x: inf)]
	],

	op_mul_False_False: [
		"false x false => @x )", 
		[(x: inf)]
	],

	op_mul_False_Int: [
		"false x 7 => @x )", 
		[(x: inf)]
	],

	op_mul_False_Float: [
		"false x 2.18 => @x )", 
		[(x: inf)]
	],
	
	op_mul_Freq_Int: [
		"2600Hz x 7 => @x )", 
		[(x:18200)]
	],

	op_mul_Freq_Noat: [
		"2600Hz x c# => @x )", 
		[(x:inf)]
	],

	op_mul_Freq_Choard: [
		"2600Hz x A# => @x )", 
		[(x: inf )]
	],

	//op_mul_Freq_False: [
	//	"2600Hz x false => @x )", 
	//	[(x: inf)]
	//],

	op_mul_Freq_Freq: [
		"2600Hz x 2600Hz )", 
		[(freq:6760000)]
	],

	//op_mul_Freq_Crap: [
	//	"2600Hz x crap => @x )", 
	//	[(x: inf)]
	//],

	
	op_mul_Freq_Float: [
		"2600Hz x 2.18 => @x )", 
		[(x: 5668 )]
	],



	op_mul_Float_Int: [
		"2.18 x 7 => @x )", 
		[(x: 15.26)]
	],

	op_mul_Float_Noat: [
		"2.18 x c# => @x )", 
		[(x: inf)]
	],

	op_mul_Float_Choard: [
		"2.18 x A# => @x )", 
		[(x: inf)]
	],

	//op_mul_Float_False: [
	//	"2.18 x false => @x )", 
	//	[(x: inf)]
	//],

	op_mul_Float_List: [
		"2.18 x <0,1> => @x )", 
		[(x: inf)]
	],

	op_mul_Float_Freq: [
		"2.18 x 2600Hz => @x )", 
		[(x: inf)]
	],

	//op_mul_Float_Crap: [
	//	"2.18 x crap => @x )", 
	//	[(x: inf)]
	//],

	op_mul_Float_Float: [
		"2.18 x 2.18 => @x )", 
		[(x: 4.36)]
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
		[(x: 0.7)]
	],
));
