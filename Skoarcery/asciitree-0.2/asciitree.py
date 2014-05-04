#!/usr/bin/env python
# -*- coding: utf-8 -*-

def draw_tree(node,
              child_iter=lambda n: n.children,
              text_str=lambda n: str(n)):
    return _draw_tree(node, '', child_iter, text_str)


def _draw_tree(node, prefix, child_iter, text_str):
    buf = StringIO()

    children = list(child_iter(node))

    # check if root node
    if prefix:
        buf.write(prefix[:-3])
        buf.write('  +--')
    buf.write(text_str(node))
    buf.write('\n')

    for index, child in enumerate(children):
        if index+1 == len(children):
            sub_prefix = prefix + '   '
        else:
            sub_prefix = prefix + '  |'

        buf.write(
            _draw_tree(child, sub_prefix, child_iter, text_str)
        )

    return buf.getvalue()


if __name__ == '__main__':
    class Node(object):
        def __init__(self, name, children):
            self.name = name
            self.children = children

        def __str__(self):
            return self.name

    root = Node('root', [
        Node('sub1', []),
        Node('sub2', [
            Node('sub2sub1', [])
        ]),
        Node('sub3', [
            Node('sub3sub1', [
                Node('sub3sub1sub1', [])
            ]),
            Node('sub3sub2', [])
        ])
    ])

    print(draw_tree(root))
