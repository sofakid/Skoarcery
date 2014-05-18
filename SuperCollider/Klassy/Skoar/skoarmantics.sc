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
        
                noad.absorb_toke;
                noad.beat = noad.toke;
                noad.is_beat = true;
            },
        
            "meter_beat" -> {
                | skoar, noad |
        
                noad.absorb_toke;
                noad.beat = noad.toke;
            },
        
            "listy" -> {
                | skoar, noad |
        /*
                X = List[];
        
                for x in noad.children[1:-1] {
                    if x.toke and isinstance(x.toke, Toke_ListSep):
                        continue
                    X.append(x)
                }
        
                noad.replace_children(X);*/
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
                    noad.performer = {_.jmp_colon(noad);};
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
                    noad.performer = {_.noat_go(noat)};
                };
            },
        
            "noat_reference" -> {
                | skoar, noad |
        
                // TODO Symbol | CurNoat | listy
            },
        
            "pedally" -> {
                | skoar, noad |
            }

         ];        
        ^dict;
    }

}