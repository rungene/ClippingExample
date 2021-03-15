package com.example.android.clippingexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

//The @JvmOverloads annotation instructs the Kotlin compiler to generate overloads for this
// function that substitute default parameter values.
class ClippedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        // Smooth out edges of what is drawn without affecting shape.
        isAntiAlias = true
        strokeWidth = resources.getDimension(R.dimen.strokeWidth)
        textSize = resources.getDimension(R.dimen.textSize)
    }

    //create and initialize a path variable of a Path to store locally the path of what has been drawn.
    private val path = Path()

    //add variables for dimensions for a clipping rectangle around the whole set of shapes.
    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

    //Add variables for the inset of a rectangle and the offset of a small rectangle.
    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)

    //Add a variable for the radius of a circle. This is the circle that is drawn inside the rectangle.
    private val circleRadius = resources.getDimension(R.dimen.circleRadius)

   // Add an offset and a text size for text that is drawn inside the rectangle.
   private val textOffset = resources.getDimension(R.dimen.textOffset)
    private val textSize = resources.getDimension(R.dimen.textSize)

   // Set up the coordinates for two columns.
   private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight

    //Add the coordinates for each row, including the final row for the transformed text.
    private val rowOne = rectInset
    private val rowTwo = rowOne + rectInset + clipRectBottom
    private val rowThree = rowTwo + rectInset + clipRectBottom
    private val rowFour = rowThree + rectInset + clipRectBottom
    private val textRow = rowFour + (1.5f * clipRectBottom)

    //Override onDraw() and call a function for each shape you are drawing.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackAndUnclippedRectangle(canvas)
        drawDifferenceClippingExample(canvas)
        drawCircularClippingExample(canvas)
        drawIntersectionClippingExample(canvas)
        drawCombinedClippingExample(canvas)
        drawRoundedRectangleClippingExample(canvas)
        drawOutsideClippingExample(canvas)
        drawSkewedTextExample(canvas)
        drawTranslatedTextExample(canvas)
        // drawQuickRejectExample(canvas)
    }

    //Create stubs for each of the drawing functions

    private fun drawBackAndUnclippedRectangle(canvas: Canvas) {
    }
    private fun drawDifferenceClippingExample(canvas: Canvas) {
    }
    private fun drawCircularClippingExample(canvas: Canvas) {
    }
    private fun drawIntersectionClippingExample(canvas: Canvas) {
    }
    private fun drawCombinedClippingExample(canvas: Canvas) {
    }
    private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
    }
    private fun drawOutsideClippingExample(canvas: Canvas) {
    }
    private fun drawTranslatedTextExample(canvas: Canvas) {
    }
    private fun drawSkewedTextExample(canvas: Canvas) {
    }
    private fun drawQuickRejectExample(canvas: Canvas) {
    }




}