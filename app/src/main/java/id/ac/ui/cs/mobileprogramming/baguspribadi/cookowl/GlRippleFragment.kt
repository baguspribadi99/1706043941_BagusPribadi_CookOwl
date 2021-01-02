package id.ac.ui.cs.mobileprogramming.baguspribadi.cookowl

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import r21nomi.com.glrippleview.GLRippleView

class GlRippleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.gl_fragment, container, false)

        val glViewObject = view.findViewById<GLRippleView>(R.id.glView)

        glViewObject.run{
            addBackgroundImages(listOf(
                BitmapFactory.decodeResource(resources, R.drawable.chef_renatta),
                BitmapFactory.decodeResource(resources, R.drawable.owl_cartoon)
            ))
            setRippleOffset(0.01f)
            setFadeInterval(2500)
            startCrossFadeAnimation()
        }

        return view
    }


    companion object{
        @JvmStatic
        fun newInstance() =
            GlRippleFragment()
    }
}