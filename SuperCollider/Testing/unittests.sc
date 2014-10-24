SkoarSanity : UnitTest {

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
     
        SkoarTests.food.do {
            | x |

            x.dump;

            x.skoar;

            // call assertions handlers

            // start processing events
        };


      	//this.assert( 6 == 6, "6 should equal 6");
      	//this.assertEquals( 9, 9, "9 should equal 9");
      	//this.assertFloatEquals( 4.0 , 1.0 * 4.0 / 4.0 * 4.0, 
      	//	"floating point math should be close to equal");
     
      	// will wait until the condition is true
      	// will be considered a failure after 10 seconds
      	//this.wait( { p.isPlaying }, "waiting for synth to play", 10);
     
    }

}

SkoarTests {

    *food {^["""
a ) )) ))) )))) ))))) )))))) )))))))
""","""
b ] ]] ]]] ]]]] ]]]]] ]]]]]] ]]]]]]]
""","""
c .] .]]. .]]]__. ]]]]__ ]]]]] ]]]]]] ]]]]]]]
""","""
} }} }}} }}}}
""","""
o/ oo/ ooo/ oooo/ ooooo/
"""
     ]}
}

