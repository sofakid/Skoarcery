SkoarTest : UnitTest {
	
	var <>tests_table;

    *report {
        super.report;
        Post.nl;
		if (failures.size > 0) {
			"SKOAR FAIL".inform;
        } {
			"SKOAR PASS".inform;
		};
    }
	
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

	test_do {
		 
		 this.assert(tests_table.notNil == true, "You didn't set up the tests right.");
		 if (tests_table.isNil) {
			^nil;
		 };

		 tests_table.keysValuesDo {
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

SkoarTestRunner {

	*new {
		| x |
		^super.new.init(x);
	}

	init {
		| tests |
		var testoar = SkoarTest.new;
		testoar.tests_table = tests;
		testoar.run;
	}
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
        expected = exp;
        testoar = tstr;
        tag = msg ++ ": ";
        end = (\dur: 0, \isRest: true, \delta: 0);
		this.catch {
			skoar = skrs.skoar;
        };
		
	}

    *test {
        | skrs, exp, tstr, msg |
        var expectoar = Expectoar.new(skrs, exp, tstr, msg);

        expectoar.run;
    }

	catch {
		| f |
		try {
			f.value();
		} {
			| e |
			testoar.assert(false == true, tag ++ "SkoarError");
			//e.postProtectedBacktrace;	
		};
	}

    run {
        var pat;
        var result;

		testoar.assert(skoar.notNil == true, "Parse Fail");
		if (skoar.isNil) {
			^nil;
		};

		this.catch {
			pat = skoar.pskoar;
		};

        expected.do {
            | ex |

            case {ex.isKindOf(Array)} {
                var n = ex.size;
                var results = Array.newClear(n);

                for (0, n-1, {
                    | i |
					this.catch {
						results.put(i, pat.nextFunc.value);
					};
                });
                "Expecting events:".postln;
                ex.do {
                    | x |
                    "   ".post; x.postln;
                    testoar.assert(this.matches(x, results) == true, tag ++ "simulfail");
                };

            } {ex.isKindOf(Event)} {
                "Expecting event: ".post; ex.postln;
				this.catch {
					result = pat.nextFunc.value;
				};
                
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

        this.catch {
			result = pat.nextFunc.value;
		};

        if (result.notNil) {
            this.match(end, result);
        };

    }

    match {
        | exp_event, seen_event |

        exp_event.keysValuesDo {
            | ekey, eval |

			if (eval == inf) {
				testoar.assert(false, "Unimplemented test: " ++ tag);
			} {
				testoar.assert(seen_event.isKindOf(Event), tag ++ "should be an event.");

				if (seen_event.notNil) {
					var seen_val = this.flatten(seen_event[ekey]);
					var x;

					if (eval.isKindOf(Float)) {
						x = eval - seen_val;
						if (x < 0) {
							x = -1 * x;
						};
						x = x < 0.0001;
					} {
						x = seen_val == eval;
					};

					testoar.assert(x, tag ++  "seen_event[" ++ ekey ++ "] = " ++ seen_val ++ " == " ++ eval ++ " expected :: <" 
										  ++ seen_val.class.asString ++ ", " ++ eval.class.asString ++ ">";
					);
				};
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
                    var x = this.flatten(seen_event[ekey]);

                    seen = seen && (x == eval);
                };
            };

            if (seen == true) {
                ^true;
            };
        };

        ^false;
    }

	flatten {
		| obj |

		if (obj.isKindOf(Skoarpuscle)) {
			obj = obj.val;
		};

		if (obj.isKindOf(Array)) {
			var a = [];
			obj.do {
				| x |
				a = a.add(this.flatten(x));
			};
			obj = a;
		};
		
		^obj;
	}
}
