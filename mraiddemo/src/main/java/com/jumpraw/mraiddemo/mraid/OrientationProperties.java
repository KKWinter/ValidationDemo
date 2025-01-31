package com.jumpraw.mraiddemo.mraid;

import java.util.Formatter;
import java.util.Locale;
import java.util.Map;

/**
 * 频繁出现字符串和枚举类型转化
 */
public class OrientationProperties {

    public static OrientationProperties propertiesFromArgs(Map<String, String> args) {
        OrientationProperties properties = new OrientationProperties();

        try {
            properties.setAllowOrientationChange(Boolean.parseBoolean(args.get(Consts.OrientationPpropertiesAllowOrientationChange)));

            String forceOrientationString = args.get(Consts.OrientationPpropertiesForceOrientation);
            if (Consts.OrientationPropertiesForceOrientationNone.equals(forceOrientationString)) {
                properties.setForceOrientation(Consts.ForceOrientation.None);
            } else if (Consts.OrientationPropertiesForceOrientationPortrait.equals(forceOrientationString)) {
                properties.setForceOrientation(Consts.ForceOrientation.Portrait);
            } else if (Consts.OrientationPropertiesForceOrientationLandscape.equals(forceOrientationString)) {
                properties.setForceOrientation(Consts.ForceOrientation.Landscape);
            }
        } catch (Exception ex) {
            // TODO: Error handling?
            // Possibly let the error bubble up so whomever is trying to parse can deal with it.
        }

        return properties;
    }

    private boolean allowOrientationChange = true;
    private Consts.ForceOrientation forceOrientation = Consts.ForceOrientation.None;

    public OrientationProperties() {

    }

    public boolean getAllowOrientationChange() {
        return allowOrientationChange;
    }

    public void setAllowOrientationChange(boolean allowOrientationChange) {
        this.allowOrientationChange = allowOrientationChange;
    }

    public Consts.ForceOrientation getForceOrientation() {
        return forceOrientation;
    }

    public void setForceOrientation(Consts.ForceOrientation forceOrientation) {
        this.forceOrientation = forceOrientation;
    }

    @Override
    public String toString() {
        String allowOrientationChangeString = allowOrientationChange ? Consts.True : Consts.False;

        String forceOrientationString = Consts.OrientationPropertiesForceOrientationNone;
        switch (forceOrientation) {
            case None:
                forceOrientationString = Consts.OrientationPropertiesForceOrientationNone;
                break;
            case Portrait:
                forceOrientationString = Consts.OrientationPropertiesForceOrientationPortrait;
                break;
            case Landscape:
                forceOrientationString = Consts.OrientationPropertiesForceOrientationLandscape;
                break;
        }

        String format = "{allowOrientationChange:'%s',forceOrientation:'%s'}";
        Formatter formatter = new Formatter(Locale.US);
        formatter.format(format, allowOrientationChangeString, forceOrientationString);
        String toString = formatter.toString();
        formatter.close();

        return toString;
    }

}
