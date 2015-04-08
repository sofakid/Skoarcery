
var qrt = (\dur:1);
var eth = (\dur:1/2);
var sxt = (\dur:1/4);

SkoarTestRunner((

    long_beats: [
        ") ). )) )). ))) )))) .))))) )))))) )))))))",
        [(\dur:1),(\dur:1.5),(\dur:2),(\dur:3),(\dur:4),(\dur:8),(\dur:16),(\dur:32),(\dur:64)]
    ],

    short_beats: [
        "] ]] ]]]  ]]]] ]]. ]]].  ]]]]] .]]]]]] ]]]]]]]",
        [(\dur:1/2), (\dur:1/4),(\dur:1/8),
         (\dur:1/16),(\dur:3/8), (\dur:3/16),
         (\dur:1/32),(\dur:1/64),(\dur:1/128)]
    ],

    long_rests: [
        "} }} }}} }}}} }. }}.",
        [(\dur:1,\isRest:true),(\dur:2,\isRest:true),
         (\dur:4,\isRest:true),(\dur:8,\isRest:true),
         (\dur:1.5,\isRest:true),(\dur:3,\isRest:true)]
    ],

    short_rests: [
        "o/ oo/ ooo/ oooo/ ooooo/   o/. oo/. ooo/.",
        [(\dur:1/2,\isRest:true),(\dur:1/4,\isRest:true),
         (\dur:1/8,\isRest:true), (\dur:1/16,\isRest:true),
         (\dur:1/32,\isRest:true),(\dur:3/4,\isRest:true),
         (\dur:3/8,\isRest:true),(\dur:3/16,\isRest:true)]
    ],

    fancy_beats: [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ],

    fancy_beats: [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ],

    listy_a: [
        "<0,1,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, 1, 2 ] )]

    ],

    listy_b: [
        "<0.0, -1, 2.2> => @food )",
        [( 'dur': 1.0, 'food': [ 0.0, -1, 2.2 ] )]

    ],

    listy_c: [
        "<<0,3>,1,2> => @food )",
        [( 'dur': 1.0, 'food': [ [0,3], 1, 2 ] )]

    ],

    listy_d: [
        "3 => @x <0,!x,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, 3, 2 ], 'x': 3 )]

    ],

    listy_e: [
        "<3,4> => @x <0,!x,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, [3,4], 2 ], 'x':[3, 4] )]

    ],

    numbers: [
        "-1 ) 0 ) 1 ) 2 ) 20 ] 0.1 ] 1.0 ]  ",
        [(\degree:-1),(\degree:0),(\degree:1),(\degree:2),
        (\degree:20),(\degree:0.1),(\degree:1.0)]
    ],

    colons_simple: [
        "|: ) ) ) :| ] ] :|",
        [
        qrt,qrt,qrt,
        qrt,qrt,qrt, eth,eth,
        qrt,qrt,qrt, eth,eth
        ]
    ],

    colons_middle: [
        "|: ) ) ) |: ] ] :|",
        [
        qrt,qrt,qrt,
        eth,eth,eth,eth
        ]
    ],

    dal_segno_a: [
        ",segno` ) ) ) |: ] ] :| fine D.S. al fine",
        [
        qrt, qrt, qrt, eth, eth, eth, eth,
        qrt, qrt, qrt, eth, eth, eth, eth
        ]
    ],

    dal_segno_b: [
        ",segno` ) ,segno` ) ) |: ] ] :| fine Dal Segno al fine",
        [
        qrt, qrt, qrt, eth, eth, eth, eth,
             qrt, qrt, eth, eth, eth, eth,
        ]
    ],

    skoarpion_a: [
        ") {! f !! ] !} !f",
        [qrt, eth]
    ],

    skoarpion_b: [
        ") {! f<x> !! !x ] !} ) !f<0>",
        [qrt, qrt, eth]
    ],

	skoarpion_c: [
		") {! <x> !! ] !} => @f ) !f<0>",
		[qrt, qrt, eth]
	],

	skoarpion_d: [
		") {! ] !} => @f ) !f",
		[qrt, qrt, eth]
	],

	one_voice_a: [
        ")
         .a ]
         )
         .a ]
        ",
        [qrt, eth, qrt, eth]
    ],

    one_voice_b: [
        ") )
         .a ] ]
        ",
        [qrt, qrt, eth, eth]
    ],

    two_voices_a: [
        ") )
         .a ]     ] ]
         .b ]] ]] )
        ",
        [[qrt,qrt], [qrt,qrt], [eth,sxt], sxt, [eth,qrt], eth]
    ],

));
