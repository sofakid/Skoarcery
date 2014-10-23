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
    var  <skoarpions;   // collection of skoarpions

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

        skoarpions = IdentityDictionary.new;

        this.skoarboard_defaults;

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
                // run the function x.name, pass the noad
                inspector[x.name].(x);

            // nonterminals*
            } {
                // run the function, pass the noad (not the nonterminal)
                skoarmantics[x.name].(this, x);
            };
        };

">>> decorating...".postln;
        tree.depth_visit(f);
"<<< decorated.".postln;

        this.decorate_voices;
    }


    // --------------------------
    // the voices of the children
    // --------------------------

    // we don't know the voices until the end of decorating, so we make a second pass.
    decorate_voices {

        tree.voice = conductoar;

        ">>> assigning voices...".postln;
        tree.assign_voices(conductoar,nil);
        "<<< all the children have voices.".postln;

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

    play {
        this.pskoar.play;
    }

    pskoar {
        ^Skoarchestra.new(this).pfunk;
    }

    pvoice {
        | voice_name |

        ^SkoarMinstrel.new(this.tree, voices[voice_name], this).pfunk;
    }
}


+String {
	skoar {
    	var r = Skoar.new(this);
    	">>> Parsing skoar...".postln;
        r.parse;
        "<<< parsed.".postln;
        "---< Undecorated Skoar Tree >---".postln;
        r.tree.draw_tree.postln;

        r.decorate;

        "---< Skoar Tree >---".postln;
        r.tree.draw_tree.postln;

        r.skoarpions.keysValuesDo {
            | k, v |
            ("---< Skoarpion " ++ k ++ " >---").postln;

            v.head.postln;
            "head:".postln;
            v.head.draw_tree.post;

            "body:".postln;
            v.body.do {|x| x.draw_tree.post;};

            if (v.stinger != nil) {
                "stinger: ".postln;
                v.stinger.draw_tree.post;
            };

            "".postln;

        };

        "Skoar parsed.".postln;
        ^r;
    }

    pskoar {
        ^this.skoar.pskoar;
	}
}
