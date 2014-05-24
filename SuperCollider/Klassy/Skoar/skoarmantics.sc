// ============
// Skoarmantics
// ============

//
// We went depth first so our children should be defined
//
Skoarmantics {

    *new {

        var dict = Dictionary[

            "msg_chain_node" -> {
                | skoar, noad |
            },
        
            "beat" -> {
                | skoar, noad |
                var t;
        
                noad.absorb_toke;
                t = noad.toke;

                noad.beat = t;
                noad.is_beat = true;
                noad.is_rest = t.is_rest;
            },
        
            "meter_beat" -> {
                | skoar, noad |
        
                noad.absorb_toke;
                noad.beat = noad.toke;
            },
        
            "listy" -> {
                | skoar, noad |

                var n = noad.children.size;
                var x = nil;
                var items = List[];

                for (1, n {
                    | i |
                    x = noad.children[i];

                    if (x.toke.isKindOf(Toke_ListSep) == false) {
                        items.add(x);
                    };
                });

                noad.replace_children(items);
            },
        
            "clef" -> {
                | skoar, noad |
            },
        
            "meter_symbolic" -> {
                | skoar, noad |
            },
        
            "stmt" -> {
                | skoar, noad |
            },
        
            "musical_keyword_misc" -> {
                | skoar, noad |
            },
        
            "coda" -> {
                | skoar, noad |
            },
        
            "meter_ass" -> {
                | skoar, noad |
            },


            "assignment" -> {
                | skoar, noad |
            },
        
            "accidentally" -> {
                | skoar, noad |
            },
        
            "boolean" -> {
                | skoar, noad |
            },
        
            "ottavas" -> {
                | skoar, noad |
            },
        
            "skoaroid" -> {
                | skoar, noad |
            },
        
            "msg" -> {
                | skoar, noad |
            },
        
            "dal_goto" -> {
                | skoar, noad |
            },
        
            "cthulhu" -> {
                | skoar, noad |
            },
        
            "dynamic" -> {
                | skoar, noad |
            },
        
            "optional_carrots" -> {
                | skoar, noad |
            },


            "meter_sig_prime" -> {
                | skoar, noad |
            },
        
            "meter" -> {
                | skoar, noad |
        
                // trim start and end tokens
                //noad.replace_children(noad.children[1:-1]);
        
        
            },
        
            "marker" -> {
                | skoar, noad |
        
                var toke;
        
                noad.absorb_toke;
                skoar.add_marker(noad);
        
                toke = noad.toke;
                if (toke.isKindOf(Toke_Bars) && toke.pre_repeat) {
                    "feed".postln;
                    noad.performer = {skoar.jmp_colon(noad);};
                };
        
            },
        
            "noaty" -> {
                | skoar, noad |
            },
        
            "noat_literal" -> {
                | skoar, noad |
        
                var noat = noad.absorb_toke;
                noad.noat = noat;
        
                if (noat.isKindOf(Toke_VectorNoat)) {
                    noad.performer = {skoar.noat_go(noat)};
                };
            },
        
            "noat_reference" -> {
                | skoar, noad |
        
                // TODO Symbol | CurNoat | listy
                if (noad.name == "listy") {

                };
            },
        
            "pedally" -> {
                | skoar, noad |
            }

        ];
        ^dict;
    }

}