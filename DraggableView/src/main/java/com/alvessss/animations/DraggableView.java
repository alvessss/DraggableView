package com.alvessss.animations;

/*
   Nada como um dia após o outro dia.
*/

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

@SuppressWarnings("all")
public class DraggableView
{
   // i think 80 is a good number
   private static final int DEGREES = 80;

   private final View draggableView;

   private final Point centerPoint;
   private final Bounds dragBounds;
   private final boolean fixed;

   public DraggableView(@NonNull final View view, float bound, boolean fixed)
   {

      draggableView = view;

      assert draggableView.isAttachedToWindow();
      draggableView.setOnTouchListener(new OnTouch());

      centerPoint = new Point(
         draggableView.getX(),
         draggableView.getY()
      );

      if (bound > 0)
      {
         float radius = view.getWidth() / 2;
         dragBounds = new Bounds(centerPoint, bound, radius);
      }
      else
      {
         dragBounds = null;
      }

      this.fixed = fixed;
   }

   public View getView()
   {
      return draggableView;
   }

   public void release()
   {
      draggableView.setOnTouchListener(null);
   }

   public void setNewCallback(View.OnTouchListener onTouch)
   {
      draggableView.setOnTouchListener(onTouch);
   }

   private class OnTouch implements View.OnTouchListener
   {
      private Point difPoint;

      @Override
      public boolean onTouch(View view, MotionEvent motionEvent)
      {
         switch (motionEvent.getActionMasked())
         {
            case MotionEvent.ACTION_DOWN:
               difPoint = new Point(
                  view.getX() - motionEvent.getRawX(),
                  view.getY() - motionEvent.getRawY()
               );
               break;

            case MotionEvent.ACTION_MOVE:
               Point currPoint = new Point(
                  motionEvent.getRawX() + difPoint.x,
                  motionEvent.getRawY() + difPoint.y
               );

               if  (dragBounds == null)
               {
                  _goTo(view, currPoint);
               }
               else
               {
                  if (dragBounds.checkPoint(currPoint))
                  {
                     _goTo(view, currPoint);
                  }
                  else
                  {
                     Point closestPont = dragBounds.getClosestPoint(currPoint);
                     _goTo(view, closestPont);
                  }
               }
               break;

            case MotionEvent.ACTION_UP:
               if (fixed)
               {
                  _goTo(view, centerPoint);
               }

            default:
               return false;
         }
         return true;
      }

      private void _goTo(View view, Point point)
      {
         view.setX(point.x);
         view.setY(point.y);
      }
   }

   private class Point
   {
      public float x;
      public float y;

      public Point(float x, float y)
      {
         this.x = x;
         this.y = y;
      }
   }

   private class Bounds
   {
      private Point[] availablePoints;
      private Point fixedPoint;
      private final float bound;

      public Bounds(Point fixedPoint, float bound, float radius)
      {
         this.fixedPoint = fixedPoint;
         this.bound = radius + bound;
         _calculateDegrees(bound, radius);
      }

      public boolean checkPoint(Point checkingPoint)
      {
         if (_getDistance(fixedPoint, checkingPoint) < bound)
         {
            return true;
         }
         return false;
      }

      public Point getClosestPoint(Point outOfBoundsPoint)
      {
         Point closest;

         float distance = _getDistance(availablePoints[0], outOfBoundsPoint);
         closest = availablePoints[0];

         for (int i = 1; i < availablePoints.length; i++)
         {
            float tempDistance = _getDistance(availablePoints[i], outOfBoundsPoint);
            if (tempDistance < distance)
            {
               distance = _getDistance(availablePoints[i], outOfBoundsPoint);
               closest = availablePoints[i];
            }
         }

         return closest;
      }

      private float _getDistance(Point point1, Point point2)
      {
         Point diference = new Point(
            point1.x - point2.x,
            point1.y - point2.y
         );

         float distance = (float) Math.sqrt(
            (diference.x * diference.x) + (diference.y * diference.y)
         );

         return distance;
      }

      private void _calculateDegrees(float bound, float radius)
      {
         availablePoints = new Point[DEGREES];
         for (int i = 0; i < DEGREES; i++)
         {
            availablePoints[i] = new Point(
               (float) (fixedPoint.x + bound + radius * Math.cos(i)),
               (float) (fixedPoint.y + bound + radius * Math.sin(i))
            );
         }
      }
   }
}