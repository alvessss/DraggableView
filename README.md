<h2>DraggableView</h2>

<p>Allows you drag a view</p>

<p>Example:</p>
    <img src="https://github.com/alvessss/DraggableView/blob/master/example.gif" width="85%" height="85%"/>


<p>Example 2:</p>

        // ...
        anyView = findViewById(...);
        float bounds = 1.0F; // limit to drag
        boolean fixed = true; // must return to original

        DraggableView draggableView = new DraggableView(anyView, bounds, fixed);

        // enjoy ...

        draggableView.release();
