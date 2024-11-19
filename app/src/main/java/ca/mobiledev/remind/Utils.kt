package ca.mobiledev.remind

import android.graphics.Matrix
import android.view.View
import android.view.ViewGroup

object Utils {
    private val sInvMatrix = Matrix()
    private val sPoint = FloatArray(2)

    /**
     * Find child View in a ViewGroup by its position (x, y)
     *
     * @param parent the ViewGroup
     * @param x the x position in parent
     * @param y the y position in parent
     * @return null if not found
     */
    @JvmStatic
    fun findChildByPosition(parent: ViewGroup, x: Float, y: Float): View? {
        val count = parent.childCount
        for (i in count - 1 downTo 0) {
            val child = parent.getChildAt(i)
            if (child.visibility == View.VISIBLE) {
                if (isPositionInChildView(parent, child, x, y)) {
                    return child
                }
            }
        }
        return null
    }

    private fun isPositionInChildView(parent: ViewGroup, child: View, x: Float, y: Float): Boolean {
        sPoint[0] = x + parent.scrollX - child.left
        sPoint[1] = y + parent.scrollY - child.top

        val childMatrix = child.matrix
        if (!childMatrix.isIdentity) {
            childMatrix.invert(sInvMatrix)
            sInvMatrix.mapPoints(sPoint)
        }

        val newX = sPoint[0]
        val newY = sPoint[1]

        return newX >= 0 && newY >= 0 && newX < child.width && newY < child.height
    }
}