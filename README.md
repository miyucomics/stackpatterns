## Stack Patterns
Hexcasting utilizes a stack as its primary computational substrate; the shape of the language so to speak. The stack is integral to the identity of Hexcasting, but in spite of that, Hexcasting has not yet fully embraced its stack. Its transformation is *incomplete* because its fundamental building blocks—patterns—can not be stacked.

This mod fixes that by removing the "backwards to undo" movement. Moving backwards 'stacks' up one z-level as a backwards stroke and you are free to draw over existing edges. Not only that, it intercepts patterns drawn containing backwards strokes and splits it up, creating a list of tamer patterns (can't go backwards but can still stack along its own edges), and pushes that list as an execution continuation frame. In short, you can draw entire Hexes in a single stroke.

Made for April Fool's, 2026