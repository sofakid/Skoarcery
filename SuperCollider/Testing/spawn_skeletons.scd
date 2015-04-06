x = SkoarOps.new;

x.addition.keysValuesDo({
	| k, v |

	/*
	math_add_a: [
		"1 + 2 ) 3 + 4 + 5 )",
		[(\degree:3),(\degree:12)]
	],*/

	v.keysValuesDo {
		| p, q |
		var a = k.asString;
		var b = p.asString;
		a = a[11..a.size-1];
		b = b[11..b.size-1];
		("op_add_" ++ a ++ "_" ++ b ++": [\n    \"" ++ a ++ " + " ++ b ++ " => @x )\",").postln;
		("    [(\x:inf)]\n],\n").postln;
	};
	"".postln;

});

