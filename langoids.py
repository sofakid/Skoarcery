#noinspection PyPep8Naming
class Production:

    def __init__(self, langoid, production):

        from tokens import Empty

        self.name = langoid

        # a list of langoids
        self.production = production

        self.derives_empty = production[0] == Empty

    def __str__(self):

        s = self.name + " -> "

        for tnt in self.production:
            s += tnt.name + " "

        return s.strip()


class Langoid:

    def __init__(self, name):
        self.name = name
        self.derives_empty = False

    def __hash__(self):
        return hash(self.name)

    def __eq__(self, other):

        #print("SNA: " + repr(self) + ":" + repr(other))

        return self.name == other.name

    def __str__(self):
        return self.name

    def __repr__(self):
        return self.name

    def __index__(self):
        return self.name


class Terminal(Langoid):

    def __init__(self, name):
        super().__init__(name)

        if name == "<e>":
            self.derives_empty = True

    def __str__(self):
        return "T_" + self.name


class Nonterminal(Langoid):

    def __init__(self, name):
        super().__init__(name)

        # list of productions
        self.production_rules = []
        self.first = set()
        self.follow = set()
        self.derives_empty = False

    def add_production(self, p):

        o = Production(self, p)

        if o.derives_empty:
            self.derives_empty = True

        self.production_rules.append(o)

    def __repr__(self):
        s = self.name + "=: "

        for p in self.production_rules:
            s += "| "

            for langoid in p.production:
                s += str(langoid) + " "

        s += "\n"
        return s


#noinspection PyPep8Naming
class MagicSet(dict):

    def __init__(self, name):
        super().__init__()

        self.name = name

    def __str__(self):
        s = ""

        for k, v in self.items():
            s += self.name + "(" + str(k) + "): " + str(v) + "\n"

        return s

    #def __getitem__(self, key):
    #    try:
    #        return self.D[key.name]
    #    except KeyError:
    #        return set()
    #
    #def __setitem__(self, key, val):
    #    S = self[key]
    #    S.update(val)
    #    self.D[key.name] = S

    def __len__(self):
        i = 0

        for S in self.values():
            i += len(S)

        return i

    def add_element(self, key, element):

        #print("add-Elemetn: " + repr(key) + str(key.__class__))

        try:
            S = self[key.name]
        except KeyError:
            S = set()

        S.add(element)
        self[key.name] = S

    def add_all_from(self, key, S):

        try:
            X = self[key.name]
        except KeyError:
            X = set()

        X.update(S)
        self[key.name] = X

    def add_all_except_e_from(self, key, S):
        from tokens import Empty

        try:
            X = self[key.name]
        except KeyError:
            X = set()

        X.update(S.intersection({Empty}))
        self[key.name] = X


FIRST = MagicSet("FIRST")
FOLLOW = MagicSet("FOLLOW")


#noinspection PyPep8Naming
def compute_firsts():

    from tokens import tokens as T
    from tokens import Empty
    from nonterminals import nonterminals as N

    global FIRST

    # do terminals first
    for X in T.values():
        FIRST[X] = {X}

    last = 0
    first_len = len(FIRST)
    while first_len > last:

        last = first_len

        for X in N.values():
            if X.derives_empty:
                FIRST.add_element(X, Empty)

            for R in X.production_rules:

                # figure out FIRST(X) first
                for Yi in R.production:

                    try:
                        S = FIRST.get(Yi)
                        #print("S:{" + repr(S) + "}")
                        if S:
                            FIRST.add_all_except_e_from(X, S)

                    except KeyError:
                        pass

                    if not Yi.derives_empty:
                        break

                # if we got to the end of the loop without breaking, add Empty
                else:
                    FIRST.add_element(X, Empty)

        first_len = len(FIRST)

    print(str(FIRST))

    return FIRST


#noinspection PyPep8Naming
def compute_first_of_sequence(list_of_langoids):
    from tokens import Empty

    global FIRST

    OUT = set()

    for Yi in list_of_langoids:

        S = FIRST[Yi]

        OUT.update(S.intersection({Empty}))

        if Empty not in S:
            break

    # if we got to the end of the loop without breaking, add Empty
    else:
        OUT.add(Empty)

    return OUT


#noinspection PyPep8Naming
def compute_follows():
    from nonterminals import nonterminals as N
    from tokens import EOF, Empty

    global FOLLOW

    # start symbol gets end symbol
    FOLLOW["skoar"] = {EOF}

    # repeat until nothing can be added to any follow set
    last = 0
    first_len = len(FOLLOW)
    while first_len > last:

        last = first_len

        for X in N.values():

            for R in X.production_rules:

                A = R.production

                # If there is a production [ A -> alpha B beta]:
                #     everything except <e> in FIRST(beta) is in FOLLOW(B)

                # examine each suffix (except last)
                n = len(A)

                if n >= 2:
                    for i in range(0, n-1):

                        B = A[i]
                        beta = A[i+1:-1]

                        S = compute_first_of_sequence(beta)
                        FOLLOW.add_all_except_e_from(B, S)

                # If there is a production [ A -> alpha B ], or [ A -> alpha B beta ] with <e> in FIRST(beta):
                #     everything in FOLLOW(A) is in FOLLOW(B)

                for i in reversed(range(0, n)):

                    B = A[i]

                    # we are at the end of the list
                    if i == n-1:
                        FOLLOW.add_all_from(X, FOLLOW[B])
                        continue

                    beta = A[i+1:-1]

                    S = compute_first_of_sequence(beta)

                    if Empty in S:
                        FOLLOW.add_all_from(X, FOLLOW[B])



