// ====================================
// We all knew there'd be a god object.
// ====================================
Skoar {

    var   skoarse;      // the skoarse code
    var  <tree;         // root node of the tree (our start symbol, skoar)
    var  <toker;        // friendly neighbourhood toker
    var   parser;       // recursive descent predictive parser
    var   inspector;    // toke inspector for decorating
    var   skoarmantics; // semantic actions
    var  <skoarboard;   // copied into event
    var  <voices;       // dictionary of voices
    var  <conductoar;   // default voice

    var   when_voices_ready;  // list of functions to run after voices have been assigned

    *new {
        | code |
        ^super.new.init(code);
    }

    init {
        | code |

        var v = nil;

        skoarse = code;
        tree = nil;
        toker = Toker(skoarse);
        parser = SkoarParser.new(this);

        inspector = SkoarTokeInspector.new;
        skoarmantics = Skoarmantics.new;
        skoarboard = IdentityDictionary.new;

        voices = IdentityDictionary.new;
        conductoar = SkoarVoice.new(this, \conductoar);
        voices[\conductoar] = conductoar;

        this.skoarboard_defaults;

        when_voices_ready = List.new;
    }

    parse {
        tree = parser.skoar(nil);
        try {
            toker.eof;
        } {
            | e |
            e.postln;
            toker.dump;
        }
    }

    skoarboard_defaults {

        // 60 bpm
        skoarboard[\tempo] = 1;

        // mp
        skoarboard[\amp] = 0.5;
    }

    put {
        | k, v |
        skoarboard[k] = v;
    }

    at {
        | k |
        ^skoarboard[k];
    }

    decorate {

        var f = {
            | x |

//"inspecting ".post; x.dump;

            // tokens*
            if (x.toke != nil) {
                // run the function x.name, pass the token
                inspector[x.name].(x.toke);

            // nonterminals*
            } {
                // run the function, pass the noad (not the nonterminal)
                skoarmantics[x.name].(this, x);
            };
        };

"decorating...".postln;
        tree.depth_visit(f);
"skoar tree decorated.".postln;

        this.decorate_voices;
    }


    // --------------------------
    // the voices of the children
    // --------------------------

    // we don't know the voices until the end of decorating, so we make a second pass.
    decorate_voices {

        tree.voice = conductoar;

        "assigning voices...".postln;
        tree.assign_voices(conductoar);
        "the children have voices.".postln;

        when_voices_ready.do {
            | f |
            f.();
        };
    }


    do_when_voices_ready {
        | f |
        when_voices_ready.add(f);
    }

    // ----
    // misc
    // ----

    get_voice {
        | k |

        var voice = nil;

        if (voices.includesKey(k)) {
            voice = voices[k];
        } {
            voice = SkoarVoice(this,k);
            voices[k] = voice;
        };

        ^voice;

    }


    cthulhu {
        | noad |

        // TODO more

        "^^(;,;)^^".postln;

        this.dump;

        "".postln;
        SkoarError("^^(;,;)^^").throw;

    }

    pskoar {
        ^SkoarVoxer.new(this).pfunk;
    }

    pvoice {
        | voice_name |

        ^SkoarVoicer.new(this.tree, voices[voice_name], this).pfunk;
    }
}


+String {
	skoar {
    	var r = Skoar.new(this);
    	"parsing skoar".postln;
        r.parse;
        "decorating parse tree".postln;
        r.decorate;

        r.tree.draw_tree.postln;
        ^r;
    }

    pskoar {
        ^this.skoar.pskoar;
	}
}
