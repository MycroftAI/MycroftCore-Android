package mycroft.ai.mycroftcore_android;

/**
 * Created by paul on 2016/06/29.
 */
import java.util.List;

        import android.widget.Button;
        import android.widget.Switch;
        import android.widget.TextView;

/**
 * Interface defined to mask the dynamic retrieval of the customizer
 * used to modify the layout of GUI components.
 *
 * @author Luca Falsina
 */
public interface ComponentModifier {

    void customizeButtons(List<Button> buttonList);

    void customizeSwitch(Switch switchSlider);

    void customizeTextView(TextView textView);

}