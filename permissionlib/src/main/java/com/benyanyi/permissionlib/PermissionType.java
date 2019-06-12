package com.benyanyi.permissionlib;

import android.Manifest;

/**
 * @author myLove
 * @date 2018-06-06 11:25
 * @email ben@yanyi.red
 * @overview
 */
public class PermissionType {
    /**
     * permission group : PHONE
     * READ_PHONE_STATE
     * CALL_PHONE
     * READ_CALL_LOG
     * WRITE_CALL_LOG
     * ADD_VOICEMAIL
     * USE_SIP
     * PROCESS_OUTGOING_CALLS
     */
    public static String PHONE = Manifest.permission.READ_PHONE_STATE;

    /**
     * permission group : CALENDAR
     * READ_CALENDAR
     * WRITE_CALENDAR
     */
    public static String CALENDAR = Manifest.permission.READ_CALENDAR;

    /**
     * permission group : CAMERA
     * CAMERA
     */
    public static String CAMERA = Manifest.permission.CAMERA;

    /**
     * permission group : CONTACTS
     * READ_CONTACTS
     * WRITE_CONTACTS
     * GET_ACCOUNTS
     */
    public static String CONTACTS = Manifest.permission.READ_CONTACTS;

    /**
     * permission group : LOCATION
     * ACCESS_FINE_LOCATION
     * ACCESS_COARSE_LOCATION
     */
    public static String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    /**
     * permission group : MICROPHONE
     * RECORD_AUDIO
     */
    public static String MICROPHONE = Manifest.permission.RECORD_AUDIO;

    /**
     * permission group : SENSORS
     * BODY_SENSORS
     */
    public static String SENSORS = Manifest.permission.BODY_SENSORS;

    /**
     * permission group : SMS
     * SEND_SMS
     * RECEIVE_SMS
     * READ_SMS
     * RECEIVE_WAP_PUSH
     * RECEIVE_MMS
     */
    public static String SMS = Manifest.permission.SEND_SMS;

    /**
     * permission group : STORAGE
     * READ_EXTERNAL_STORAGE
     * WRITE_EXTERNAL_STORAGE
     */
    public static String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
}
