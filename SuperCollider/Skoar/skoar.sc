// =====
// Skoar
// =====
Skoar : Object {

    var   skoarse;      // the skoarse code
    var   parser;       // the parser
    var  <tree;         // root of tree
    var  <toker;        // toker
    var  <voices;       // all the voices
    var  <all_voice;    // the all voice
    var  <skoarpions;   // all the skoarpions

    *new {
        | code |
        ^super.new.init(code);
    }

    init {
        | code |
        var start_time;
        var parse_time;
        var decorate_time;

        skoarse = code ++ "\n";
        tree = nil;
        toker = Toker(skoarse);
        parser = SkoarParser.new(this);

        voices = IdentityDictionary.new;
        all_voice = SkoarKoar.new(this, \all);
        voices[\all] = all_voice;

        skoarpions = List[];

        start_time = Process.elapsedTime;

        ">>> parsing skoar...".postln;
        tree = parser.skoar(nil);

        try {
            toker.eof;
        } {
            | e |
            e.postln;
            toker.dump;
            e.throw;
        };

        parse_time = (Process.elapsedTime - start_time).round(0.01);

        //"---< Undecorated Skoar Tree >---".postln;
        //tree.draw_tree.postln;

        "<<< tree created, now decorating...".postln;
        this.decorate;
        decorate_time = (Process.elapsedTime - start_time - parse_time).round(0.01);

        //this.draw_skoarpions;

        debug("Skoar parsed in " ++ parse_time ++ " seconds, decorated in  "
            ++ decorate_time ++ ". Total: " ++ (parse_time + decorate_time) ++ " sec.");


    }

    decorate {

        var inspector = SkoarTokeInspector.new;
        var skoarmantics = Skoarmantics.new;

        var f = {
            | noad |
            var t = noad.toke;

            if (t.notNil) {
                var g = inspector[t.class.asSymbol];

                if (g.isKindOf(Function)) {
                    g.(this, noad, t);
                };
            } {
                var g = skoarmantics[noad.name];

                if (g.isKindOf(Function)) {
                    g.(this, noad);
                };
            };
        };

        tree.depth_visit(f);
    }

    // ----
    // misc
    // ----

    // creates a new one if needed
    get_voice {
        | k |

        var voice = nil;

        if (voices.includesKey(k)) {
            voice = voices[k];
        } {
            voice = SkoarKoar(this, k);
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

    draw_skoarpions {
        skoarpions.do {
            | x |
            x.post_tree;

            /*"Projections: ".postln;
            voices.keysDo {
                | koar_name |
                var projection = x.projection(koar_name);

                projection.block.draw_tree.postln;
            };*/
        };
    }
}


+String {
	skoar {
        ^Skoar(this);
    }

    pskoar {
        ^this.skoar.pskoar;
	}
}
