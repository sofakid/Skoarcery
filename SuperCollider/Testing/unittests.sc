SkoarTest : UnitTest {

    *report {
        super.report;
        Post.nl;
		if (failures.size > 0) {
			"SKOAR FAIL".inform;
        } {
			"SKOAR PASS".inform;
		};
    }

}

TestSkoarSanity : SkoarTest {

    var <>skoarse;

    setUp {
        // this will wait until the server is booted
      	//this.bootServer;
        // if the server is already booted it will free all nodes
      	// and create new allocators, giving you a clean slate
    }

    tearDown {
        // this will be called after each test
    }

  	//this.assert( 6 == 6, "6 should equal 6");
  	//this.assertEquals( 9, 9, "9 should equal 9");
  	//this.assertFloatEquals( 4.0 , 1.0 * 4.0 / 4.0 * 4.0,
  	//	"floating point math should be close to equal");

  	// will wait until the condition is true
  	// will be considered a failure after 10 seconds
  	//this.wait( { p.isPlaying }, "waiting for synth to play", 10);

    test_expectations {

        SkoarTests.sanity.keysValuesDo {
            | test_name, test_meat |
            var s = test_meat[0];
            var a = test_meat[1];
            var msg = test_name.asString;

            ("Test: " ++ test_name).postln;
            "Test input: ".post; s.postln;
            "Expectations:".postln; a.do {
                | x |
                x.postln;
            };

            Expectoar.test(s, a, this, msg);

        };

    }

    test_multikoar {

        SkoarTests.multikoar.keysValuesDo {
            | test_name, test_meat |
            var s = test_meat[0];
            var a = test_meat[1];
            var msg = test_name.asString;

            ("Test: " ++ test_name).postln;
            "Test input: ".post; s.postln;
            "Expectations:".postln; a.do {
                | x |
                x.postln;
            };

            Expectoar.test(s, a, this, msg);

        };

    }


}

SkoarTests {

*sanity {
    var qrt = (\dur:1);
    var eth = (\dur:1/2);

    ^IdentityDictionary[

    \long_beats -> [
        ") ). )) )). ))) )))) .))))) )))))) )))))))",
        [(\dur:1),(\dur:1.5),(\dur:2),(\dur:3),(\dur:4),(\dur:8),(\dur:16),(\dur:32),(\dur:64)]
    ],

    \short_beats -> [
        "] ]] ]]]  ]]]] ]]. ]]].  ]]]]] .]]]]]] ]]]]]]]",
        [(\dur:1/2), (\dur:1/4),(\dur:1/8),
         (\dur:1/16),(\dur:3/8), (\dur:3/16),
         (\dur:1/32),(\dur:1/64),(\dur:1/128)]
    ],

    \long_rests -> [
        "} }} }}} }}}} }. }}.",
        [(\dur:1,\isRest:true),(\dur:2,\isRest:true),
         (\dur:4,\isRest:true),(\dur:8,\isRest:true),
         (\dur:1.5,\isRest:true),(\dur:3,\isRest:true)]
    ],

    \short_rests -> [
        "o/ oo/ ooo/ oooo/ ooooo/   o/. oo/. ooo/.",
        [(\dur:1/2,\isRest:true),(\dur:1/4,\isRest:true),
         (\dur:1/8,\isRest:true), (\dur:1/16,\isRest:true),
         (\dur:1/32,\isRest:true),(\dur:3/4,\isRest:true),
         (\dur:3/8,\isRest:true),(\dur:3/16,\isRest:true)]
    ],

    \fancy_beats -> [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ],

    \fancy_beats -> [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ],

    \listy_a -> [
        "<0,1,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, 1, 2 ] )]

    ],

    \listy_b -> [
        "<0.0, -1, 2.2> => @food )",
        [( 'dur': 1.0, 'food': [ 0.0, -1, 2.2 ] )]

    ],

    \listy_c -> [
        "<<0,3>,1,2> => @food )",
        [( 'dur': 1.0, 'food': [ [0,3], 1, 2 ] )]

    ],

    \listy_d -> [
        "3 => @x <0,!x,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, 3, 2 ], 'x': 3 )]

    ],


    \listy_e -> [
        "<3,4> => @x <0,!x,2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, [3,4], 2 ], 'x':[3, 4] )]

    ],

    \listy_f -> [
        "<3,4> => @x <0, !x.next, !x.next, 2> => @food )",
        [( 'dur': 1.0, 'food': [ 0, 3, 4, 2 ], 'x':[3, 4] )]

    ],

/*    \numbers -> [
        "-1 ) 0 ) 1 ) 2 ) 20 ] 0.1 ] 1.0 ]  ",
        [(\degree:-1),(\degree:0),(\degree:1),(\degree:2),
        (\degree:20),(\degree:0.1),(\degree:1.0)]
    ],*/

    \colons_simple -> [
        "|: ) ) ) :| ] ] :|",
        [
        qrt,qrt,qrt,
        qrt,qrt,qrt, eth,eth,
        qrt,qrt,qrt, eth,eth
        ]
    ],

    \colons_middle -> [
        "|: ) ) ) |: ] ] :|",
        [
        qrt,qrt,qrt,
        eth,eth,eth,eth
        ]
    ],

    \colons_unbalanced -> [
        "| ) ) ) :| ] ] :|",
        [
        qrt, qrt, qrt,
        qrt, qrt, qrt, eth, eth,
        qrt, qrt, qrt, eth, eth,
        ]
    ],

    \da_capo_a -> [
        ") ) ) fine ] ] D.C. al fine",
        [
        qrt, qrt, qrt, eth, eth,
        qrt, qrt, qrt
        ]
    ],

    \da_capo_b -> [
        ") ) ) |: ] ] :| fine D.C. al fine",
        [
        qrt, qrt, qrt, eth, eth, eth, eth,
        qrt, qrt, qrt, eth, eth, eth, eth
        ]
    ],

    \dal_segno_a -> [
        ",segno` ) ) ) |: ] ] :| fine D.S. al fine",
        [
        qrt, qrt, qrt, eth, eth, eth, eth,
        qrt, qrt, qrt, eth, eth, eth, eth
        ]
    ],

    \dal_segno_b -> [
        ",segno` ) ,segno` ) ) |: ] ] :| fine Dal Segno al fine",
        [
        qrt, qrt, qrt, eth, eth, eth, eth,
             qrt, qrt, eth, eth, eth, eth,
        ]
    ],

    \skoarpion_a -> [
        ") {! f !! ] !} !f",
        [qrt, eth]
    ],

    \skoarpion_b -> [
        ") {! f<x> !! !x ] !} !f<0>",
        [qrt, eth]
    ],

    \skoarpion_c -> [
        ") {! <x> !! ] !} => @f !f",
        [qrt, eth]
    ],

    \skoarpion_d -> [
        ") {! f !! ] !! 'food' !} !f",
        [qrt, (\string: "food")]
    ],
]}


*sanity_simple {^[
    "a ) )) ))) )))) ))))) )))))) )))))))",
    "b ] ]] ]]] ]]]] ]]]]] ]]]]]] ]]]]]]]",
    "c .] .]]. .]]]__. ]]]]__ ]]]]] ]]]]]] ]]]]]]]",
    "} }} }}} }}}} ",
    "o/ oo/ ooo/ oooo/ ooooo/",
    "_a _b _c# _d _e _f _g a b c d e f g",
    "^o/ {! <x> !! !x !}",
    "1 2.18 @foo @foo.postln",
    "@foo !foo.postln",
    "<'mic check',1,2>",
    "{! f<x,y,z> !! !x ] ] !y ] !z ] !} !f<_a,c,e>.next",
    "{! f<x,y,z> !! !x ] ] !y ] !z ] !} !f<_a,c,e,g>.next",
    "{! f<x,y,z> !! !x ] ] !y ] !z ] !} !f<_a,c>.next",
    "{! f<x,y,z> !! !x ] ] !y ] !z ] !} !f.next",
    "^^(;,;)^^"
    ]}

*multikoar {
    var qrt = (\dur:1);
    var eth = (\dur:1/2);
    var sxt = (\dur:1/4);


    ^IdentityDictionary[

    \one_voice_a -> [
        ")
         .a ]
         )
         .a ]
        ",
        [qrt, eth, qrt, eth]
    ],

    \one_voice_b -> [
        ") )
         .a ] ]
        ",
        [qrt, qrt, eth, eth]
    ],

    \two_voices_a -> [
        ") )
         .a ]     ] ]
         .b ]] ]] )
        ",
        [[qrt,qrt], [qrt,qrt], [eth,sxt], sxt, [eth,qrt], eth]
    ],

]}

}

Expectoar {

    var <skoar;
    var <expected;
    var testoar;
    var tag;
    var end;

    *new {
        | skrs, exp, tstr, msg |
        ^super.new.init(skrs, exp, tstr, msg);
    }

    init {
        | skrs, exp, tstr, msg |
        skoar = skrs.skoar;
        expected = exp;
        testoar = tstr;
        tag = msg ++ ": ";
        end = (\dur: 0, \isRest: true, \delta: 0);
    }

    *test {
        | skrs, exp, tstr, msg |
        var expectoar = Expectoar.new(skrs, exp, tstr, msg);

        expectoar.run;
    }

    run {
        var pat = skoar.pskoar;
        var result;

        expected.do {
            | ex |

            case {ex.isKindOf(Array)} {
                var n = ex.size;
                var results = Array.newClear(n);

                for (0, n-1, {
                    | i |
                    results.put(i, pat.nextFunc.value);
                });
                "Expecting events:".postln;
                ex.do {
                    | x |
                    "   ".post; x.postln;
                    testoar.assert(this.matches(x, results) == true, tag ++ "simulfail");
                };

            } {ex.isKindOf(Event)} {
                "Expecting event: ".post; ex.postln;
                result = pat.nextFunc.value;
                testoar.assert(result.notNil, tag ++ "expect a result");

                // that assert doesn't abort.
                if (result.notNil) {
                    this.match(ex, result);
                };

            } {ex.isKindOf(Function)} {
                // note we don't consume anything here
                ex.value(testoar, skoar);

            };

        };

        result = pat.nextFunc.value;
        if (result.notNil) {
            this.match(end, result);
        };

    }

    match {
        | exp_event, seen_event |

        exp_event.keysValuesDo {
            | ekey, eval |

            testoar.assert(seen_event.isKindOf(Event), tag ++ "not an event.");

            if (seen_event.notNil) {
                var seen_val = seen_event[ekey];

                if (seen_val.isKindOf(Skoarpuscle)) {
                    seen_val = seen_val.val;
                };

                testoar.assert(seen_val == eval, tag ++
                    "seen_event[" ++ ekey ++ "] = " ++ seen_val ++ " == " ++ eval ++ " expected"
                );
            };
        };
    }


    matches {
        | exp_event, seen_events |
        seen_events.do {
            | seen_event |
            var seen = true;

            exp_event.keysValuesDo {
                | ekey, eval |
                if (seen_event.notNil) {
                    var x = seen_event[ekey];
                    seen = seen && (x == eval);
                };
            };

            if (seen == true) {
                ^true;
            };
        };

        ^false;
    }
}
