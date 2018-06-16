package constants;

/**
 * Created by Vult on 05-Jun-18.
 * Class containing the constant for the activation key
 * Don't know if the verification should be done here or not...
 */
public class ActivationKey {
    private static final String ACTIVATION_KEY = "activateproduct-1234";

    public static boolean verifyActivationKey(String activationKey) {
        return activationKey.toLowerCase().equals(ACTIVATION_KEY.toLowerCase());
    }
}
