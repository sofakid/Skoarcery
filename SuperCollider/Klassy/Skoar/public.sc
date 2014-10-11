

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
        ^SkoarIterator.new(this.skoar).pfunk;
	}
}
