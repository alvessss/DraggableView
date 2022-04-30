<h2>DraggableView</h2>

[![](https://jitpack.io/v/alvessss/DraggableView.svg)](https://jitpack.io/#alvessss/DraggableView)

<p>Allows you drag a view</p>

<p>Example:</p>
    <img src="https://github.com/alvessss/DraggableView/blob/master/example.gif" width="85%" height="85%"/>


<p>Example 2:</p>

        // ...
        anyView = findViewById(...);
        float bounds = 1.0F; // limit to drag
        boolean fixed = true; // must return to original position

        DraggableView draggableView = new DraggableView(anyView, bounds, fixed);

        // enjoy ...

        draggableView.release();

<p>Get this:</p>

   Click in the Jitpack badge beside the Title.
