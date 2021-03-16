package com.example.android.clippingexample

import android.content.Context
import android.graphics.*
import android.os.Build
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

    //Create a drawClippedRectangle() method that takes an argument canvas of type Canvas

    private fun drawClippedRectangle(canvas: Canvas){
        canvas.clipRect(
                //method reduces the region of the screen that future draw operations can write to.
                // It sets the clipping boundaries to be the spatial intersection of the current
                // clipping rectangle and the rectangle passed into clipRect().
                clipRectLeft,clipRectTop,
                clipRectRight,clipRectBottom
        )
        //ill the canvas with white color. Only the region inside the clipping rectangle
        // will be filled!
        canvas.drawColor(Color.WHITE)

        //Change the color to red and draw a diagonal line inside the clipping rectangle.
        paint.color = Color.RED
        canvas.drawLine(
                clipRectLeft,clipRectTop,
                clipRectRight,clipRectBottom,paint
        )

      //  Set the color to green and draw a circle inside the clipping rectangle.
        paint.color =Color.GREEN
        canvas.drawCircle(
                circleRadius,clipRectBottom - circleRadius,
                circleRadius,paint
        )

        //Set the color to blue and draw text aligned with the right edge of the clipping rectangle.
        paint.color =Color.BLUE
        //align the Right side of the text with the origin
        paint.textSize =textSize
        //Note: The Paint.Align property specifies which side of the text to align to the origin
        // (not which side of the origin the text goes, or where in the region it is aligned!).
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText(
                context.getString(R.string.clipping),
                clipRectRight,textOffset,paint
        )

    }


    //Create stubs for each of the drawing functions

    private fun drawBackAndUnclippedRectangle(canvas: Canvas) {

        canvas.drawColor(Color.GRAY)
        // Save the canvas
        canvas.save()
        // Translate to the first row and column position.
        canvas.translate(columnOne,rowOne)
        //Draw by calling drawClippedRectangle().
        drawClippedRectangle(canvas)
       // restore the canvas to its previous state.
        canvas.restore()

    }
    private fun drawDifferenceClippingExample(canvas: Canvas) {
        canvas.save()
        // Move the origin to the right for the next rectangle.
        canvas.translate(columnTwo,rowOne)
        // Use the subtraction of two clipping rectangles to create a frame.
        canvas.clipRect(
                2 * rectInset,2 * rectInset,
                clipRectRight - 2 * rectInset,
                clipRectBottom - 2 * rectInset
        )
        // The method clipRect(float, float, float, float, Region.Op
        // .DIFFERENCE) was deprecated in API level 26. The recommended
        // alternative method is clipOutRect(float, float, float, float),
        // which is currently available in API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            canvas.clipRect(
                    4 * rectInset,4 * rectInset,
                    clipRectRight - 4 * rectInset,
                    clipRectBottom - 4 * rectInset,
                    Region.Op.DIFFERENCE
            )
        else {
            canvas.clipOutRect(
                    4 * rectInset,4 * rectInset,
                    clipRectRight - 4 * rectInset,
                    clipRectBottom - 4 * rectInset
            )
        }
        drawClippedRectangle(canvas)
        canvas.restore()
    }
    private fun drawCircularClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnOne, rowTwo)
        // Clears any lines and curves from the path but unlike reset(),
        // keeps the internal data structure for faster reuse.
        path.rewind()
        path.addCircle(
                circleRadius,clipRectBottom - circleRadius,
                circleRadius,Path.Direction.CCW
        )
        // The method clipPath(path, Region.Op.DIFFERENCE) was deprecated in
        // API level 26. The recommended alternative method is
        // clipOutPath(Path), which is currently available in
        // API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(path, Region.Op.DIFFERENCE)
        } else {
            canvas.clipOutPath(path)
        }
        drawClippedRectangle(canvas)
        canvas.restore()
    }

    private fun drawIntersectionClippingExample(canvas: Canvas) {
        canvas.save()
        canvas.translate(columnTwo,rowTwo)
        canvas.clipRect(
                clipRectLeft,clipRectTop,
                clipRectRight - smallRectOffset,
                clipRectBottom - smallRectOffset
        )
        // The method clipRect(float, float, float, float, Region.Op
        // .INTERSECT) was deprecated in API level 26. The recommended
        // alternative method is clipRect(float, float, float, float), which
        // is currently available in API level 26 and higher.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipRect(
                    clipRectLeft + smallRectOffset,
                    clipRectTop + smallRectOffset,
                    clipRectRight,clipRectBottom,
                    Region.Op.INTERSECT
            )
        } else {
            canvas.clipRect(
                    clipRectLeft + smallRectOffset,
                    clipRectTop + smallRectOffset,
                    clipRectRight,clipRectBottom
            )
        }
        drawClippedRectangle(canvas)
        canvas.restore()
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