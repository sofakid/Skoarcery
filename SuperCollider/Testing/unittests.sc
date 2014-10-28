SkoarSanity : UnitTest {

    var <>skoarse;

    *new {
        | skrs |
        ^super.new.init(skrs)
    }

    init {
        | skrs |
        skoarse = skrs;
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
     
    test_basic {
     
        skoarse.skoar;

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
    "{! f<x,y,z> !! @x ] ] @y ] @z ] !} !f<_a,c,e>.next",
    "{! f<x,y,z> !! @x ] ] @y ] @z ] !} !f<_a,c,e,g>.next",
    "{! f<x,y,z> !! @x ] ] @y ] @z ] !} !f<_a,c>.next",
    "{! f<x,y,z> !! @x ] ] @y ] @z ] !} !f.next",
    "^^(;,;)^^"
    ]}
}

