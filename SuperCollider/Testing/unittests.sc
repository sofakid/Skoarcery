
TestSkoarSanity : UnitTest {

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
     
    test_basic {
     
        var a = SkoarTests.sanity_simple;

        a.do {
            | skoarse |
            skoarse.skoar;
        };

      	//this.assert( 6 == 6, "6 should equal 6");
      	//this.assertEquals( 9, 9, "9 should equal 9");
      	//this.assertFloatEquals( 4.0 , 1.0 * 4.0 / 4.0 * 4.0, 
      	//	"floating point math should be close to equal");
     
      	// will wait until the condition is true
      	// will be considered a failure after 10 seconds
      	//this.wait( { p.isPlaying }, "waiting for synth to play", 10);
     
    }

    test_expectations {

        SkoarTests.sanity.keysValuesDo {
            | test_name, test_meat |
            var s = test_meat[0];
            var a = test_meat[1];
            var msg = test_name.asString;

            ("Test: " ++ test_name).postln;
            "Test input: ".post; s.postln;
            "Expectations: ".post; a.dump;

            Expectoar.test(s, a, this, msg);

        };

    }

}

SkoarTests {

*sanity {^IdentityDictionary[

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
        [(\dur:1),(\dur:2),(\dur:4),(\dur:8),(\dur:1.5),(\dur:3)]
    ],

    \short_rests -> [
        "o/ oo/ ooo/ oooo/ ooooo/   o/. oo/. ooo/.",
        [(\dur:1/2),(\dur:1/4),(\dur:1/8), (\dur:1/16),(\dur:1/32),
         (\dur:3/4),(\dur:3/8),(\dur:3/16)]
    ],

    \fancy_beats -> [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ],

    \fancy_beats -> [
        ".] .]]. ]. .)__ .)__. ]__.",
        [(\dur:1/2),(\dur:3/8),(\dur:3/4),(\dur:1),(\dur:1.5),(\dur:3/4)]
    ]






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
}

Expectoar {

    var <skoar;
    var <expected;
    var testoar;
    var tag;

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
    }

    *test {
        | skrs, exp, tstr, msg |
        var expectoar = Expectoar.new(skrs, exp, tstr, msg);

        expectoar.run;
    }

    run {
        var pat = skoar.pskoar;

        expected.do {
            | ex |

            "Expectation: ".post; ex.postln;

            case {ex.isKindOf(Array)} {
                var n = ex.size;
                var results = Array.new(n);

                ex.do {
                    results.add(pat.next);
                };

            } {ex.isKindOf(Event)} {
                debug("attempting to match.");
                this.match(ex, pat.nextFunc.value);

            } {ex.isKindOf(Function)} {
                ex.value(testoar, skoar);

            };

        };
    }

    match {
        | exp_event, seen_event |

//"blurg;".postln;
        exp_event.keysValuesDo {
            | ekey, eval |

            var x, y;

            x = seen_event[ekey];
            testoar.assert(seen_event.isKindOf(Event), tag ++ "is x an event?");

            testoar.assert(seen_event[ekey] == eval, tag ++
                "seen_event[" ++ ekey ++ "] = " ++ x ++ " == " ++ eval ++ " expected"
            );
        };

//"blarg;".postln;

    }
}
