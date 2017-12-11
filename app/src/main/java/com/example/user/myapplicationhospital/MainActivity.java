package com.example.user.myapplicationhospital;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Locale;
import java.util.Random;

//http://www.fromtexttospeech.com/

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FIRST_STAGE = "first";
    private static final String SECOND_STAGE = "second";
    private static final String THIRD_STAGE = "third";
    private static final String FOURTH_STAGE = "fourth";
    private BottomSheetBehavior bottomSheetBehavior;
    private LottieAnimationView extraAnimation;
    private FrameLayout bottomSheet;
    private boolean selectCashAsked;
    private boolean selectIdAsked;
    private boolean selectInsuranceAsked;
    private boolean selectPrescriptionAsked;
    private boolean enableMic;
    private boolean isRecording;
    private ImageView profileImageView;
    private ImageView extraImageView;
    private ImageView micImageView;
    private ImageView mainImageView;
    private TextView profileTextView;
    private TextView extraTextView;
    private Toast errorToast;
    private View includeBottomScreen;
    private ConstraintLayout micWalletContainer;
    private Button nextButton;
    private MediaPlayer mediaPlayer;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private String doctorNameSelected;
    private int bodyProblem;
    private int randFever;
    private boolean haveFileAsked;
    private boolean haveAppointmentAsked;
    private boolean tellNameAsked;
    private boolean whichDayAsked;
    private String daySelected;
    private boolean morAfterEveAsked;
    private String mAESelected;
    private boolean whatTimeAsked;
    private String timeSelected;
    private boolean haveInsuranceAsked;
    private boolean bodyPartHighlighted;
    private boolean forHowLongAsked;
    private boolean haveAppointment;
    private boolean haveFeverAsked;
    private String userName;
    private Random random = new Random();
    private Bundle params = new Bundle();
    private boolean firstStageCompleted;
    private boolean secondStageCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        bottomSheet = (FrameLayout) findViewById(R.id.bottom_sheet);
        micWalletContainer = (ConstraintLayout) findViewById(R.id.mic_wallet_container);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        profileTextView = (TextView) findViewById(R.id.profile_textView);
        extraTextView = (TextView) findViewById(R.id.extra_textView);
        profileImageView = (ImageView) findViewById(R.id.profile_imageView);
        extraImageView = (ImageView) findViewById(R.id.extra_imageView);
        mainImageView = (ImageView) findViewById(R.id.main_imageView);
        micImageView = (ImageView) findViewById(R.id.mic_imageView);
        includeBottomScreen = (View) findViewById(R.id.include_bottom_screen);
        nextButton = (Button) findViewById(R.id.next_button);
        extraAnimation = (LottieAnimationView) findViewById(R.id.extra_animation);
        micImageView.setOnTouchListener(this);
        speechRecognizer.setRecognitionListener(this);
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    private void initSpeechRecognition() {
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        speechIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
    }

    private void startStage(String stage) {
        switch (stage) {
            case FIRST_STAGE:
                firstStage();
                break;
            case SECOND_STAGE:
                secondStage();
                break;
            case THIRD_STAGE:
                thirdStage();
                break;
            case FOURTH_STAGE:
                fourthStage();
                break;
        }
    }

    private void firstStage() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 8:
                        profileImageView.setVisibility(View.VISIBLE);
                        profileTextView.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("How can i help you");
                        textToSpeech.speak("How can i help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case 5:
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("I would like to see a doctor");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_see_doc);
                        mediaPlayer.start();
                        break;
                    case 2:
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("Which doctor?");
                        textToSpeech.speak("Which doctor?", TextToSpeech.QUEUE_FLUSH, null, null);
                        extraImageView.setImageResource(R.drawable.doctors);
                        extraImageView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        enableMic = true;
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void secondStage() {
        new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 6:
                        profileImageView.setVisibility(View.VISIBLE);
                        profileTextView.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("Here it is");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_it_is);
                        mediaPlayer.start();
                        break;
                    case 4:
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("Please, go to the clinic");
                        textToSpeech.speak("Please, go to the clinic", TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case 2:
                        firstStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void thirdStage() {
        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 18:
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("Here it is");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_it_is);
                        mediaPlayer.start();
                        break;
                    case 16:
                        profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                        profileTextView.setTextSize(15);
                        profileTextView.setText("Please wait, the nurse will call your name");
                        textToSpeech.speak("Please wait, the nurse will call your name", TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case 13:
                        profileTextView.setTextSize(18);
                        includeBottomScreen.setVisibility(View.INVISIBLE);
                        mainImageView.setImageResource(R.drawable.time_lapse);
                        break;
                    case 10:
                        mainImageView.setImageResource(R.drawable.waiting_area);
                        includeBottomScreen.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("Mr. John");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_name_call);
                        mediaPlayer.start();
                        break;
                    case 8:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Yes");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
                        mediaPlayer.start();
                        break;
                    case 6:
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("Please, come with me");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_come_with_me);
                        mediaPlayer.start();
                        break;
                    case 4:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Ok");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
                        mediaPlayer.start();
                        break;
                    case 2:
                        secondStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void fourthStage() {
        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 18:
                        profileImageView.setImageResource(R.drawable.doctor_in_office);
                        profileTextView.setTextSize(14);
                        profileTextView.setText("I would like to have your blood sample test report");
                        textToSpeech.speak("I would like to have your blood sample test report", TextToSpeech.QUEUE_FLUSH, null, null);
//                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_it_is);
//                        mediaPlayer.start();
                        break;
                    case 15:
                        profileImageView.setImageResource(R.drawable.doctor_in_office);
                        profileTextView.setText("Kindly go to the lab");
                        profileTextView.setTextSize(18);
                        textToSpeech.speak("Kindly go to the lab", TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case 13:
                        profileTextView.setTextSize(18);
                        includeBottomScreen.setVisibility(View.INVISIBLE);
                        mainImageView.setImageResource(R.drawable.time_lapse);
                        break;
                    case 10:
                        mainImageView.setImageResource(R.drawable.waiting_area);
                        includeBottomScreen.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("Mr. John");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_name_call);
                        mediaPlayer.start();
                        break;
                    case 8:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Yes");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
                        mediaPlayer.start();
                        break;
                    case 6:
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("Please, come with me");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_come_with_me);
                        mediaPlayer.start();
                        break;
                    case 4:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Ok");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
                        mediaPlayer.start();
                        break;
                    case 2:
                        secondStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void onClickWallet(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void onClickWalletItem(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        int viewId = view.getId();
        if (selectCashAsked && viewId == R.id.cash_imageView) {
            Log.d(TAG, "cash");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(THIRD_STAGE);
            selectCashAsked = false;
        } else if (selectIdAsked && viewId == R.id.id_card_imageView) {
            Log.d(TAG, "id");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(SECOND_STAGE);
            selectIdAsked = false;
        } else if (selectInsuranceAsked && viewId == R.id.insurance_card_imageView) {
            Log.d(TAG, "insurance");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(THIRD_STAGE);
            selectInsuranceAsked = false;
        } else if (selectPrescriptionAsked) {
            Log.d(TAG, "prescription");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            selectPrescriptionAsked = false;
        }
    }

    public void onClickNextButton(View view) {
        if (firstStageCompleted) {
            firstStageCompleted = false;
            new CountDownTimer(16000, 1000) {
                @Override
                public void onTick(long l) {
                    switch ((int) (l / 1000)) {
                        case 13:
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            includeBottomScreen.setVisibility(View.INVISIBLE);
                            mainImageView.setImageResource(R.drawable.time_lapse);
                            nextButton.setVisibility(View.GONE);
                            break;
                        case 10:
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.GONE);
                            includeBottomScreen.setVisibility(View.VISIBLE);
                            mainImageView.setImageResource(R.drawable.reception_2nd);
                            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                            profileTextView.setText("Mr. John");
                            textToSpeech.speak("Mr. John?", TextToSpeech.QUEUE_FLUSH, null, null);
                            break;
                        case 8:
                            profileImageView.setImageResource(R.drawable.man_hospital_2nd_reception);
                            profileTextView.setText("Yes");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
                            mediaPlayer.start();
                            break;
                        case 7:
                            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                            profileTextView.setText("Do you have insurance?");
                            textToSpeech.speak("Do you have insurance?", TextToSpeech.QUEUE_FLUSH, null, null);
                            haveInsuranceAsked = true;
                            break;
                        case 5:
                            enableMic = true;
                            break;

                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }

        if (secondStageCompleted) {
            secondStageCompleted = false;
            new CountDownTimer(13000, 1000) {
                @Override
                public void onTick(long l) {
                    switch ((int) (l / 1000)) {
                        case 10:
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            includeBottomScreen.setVisibility(View.INVISIBLE);
                            mainImageView.setImageResource(R.drawable.time_lapse);
                            nextButton.setVisibility(View.GONE);
                            break;
                        case 7:
                            nextButton.setVisibility(View.GONE);
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            includeBottomScreen.setVisibility(View.VISIBLE);
                            mainImageView.setImageResource(R.drawable.doctors_office);
                            profileImageView.setImageResource(R.drawable.doctor_in_office);
                            profileTextView.setText("What's the problem?");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_whats_the_problem);
                            mediaPlayer.start();
                            break;
                        case 4:
                            bodyPartHighlighted = true;
                            extraAnimation.setVisibility(View.VISIBLE);
                            extraAnimation.setAnimation("body.json");
                            extraAnimation.playAnimation(1, 20);
                            bodyProblem = new Random().nextInt(3);
                            switch (bodyProblem) {
                                case 0:
                                    extraAnimation.playAnimation(21, 40);
                                    extraAnimation.loop(true);
                                    break;
                                case 1:
                                    extraAnimation.playAnimation(42, 60);
                                    extraAnimation.loop(true);
                                    break;
                                case 2:
                                    extraAnimation.playAnimation(62, 80);
                                    extraAnimation.loop(true);
                                    break;
                            }
                            break;
                        case 2:
                            enableMic = true;
                            break;

                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }
    }

    private  String getErrorMessage(int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "Audio isRecording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "Sorry I didn't hear you";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "I am busy processing your speech";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "Sorry I didn't hear you";
                break;
            default:
                errorMessage = "Didn't understand, please try again.";
        }
        return errorMessage;
    }

    private void checkResult(String result) {
        Log.d(TAG, "checkResult approached!");
        result = result.toLowerCase();
        Log.d(TAG, result);
        String[] doctorNames = new String[]{"dr nancy", "dr ben", "doctor nancy", "doctor ben"};
        String[] daysOfWeek = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        String[] timeOfDay = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        String[] whatIsTheProblem = new String[]{"headache", "stomachache", "chest pain", "head ache", "stomach ache", "chestpain"};
        String[] problemForHowLong = new String[]{"1 day", "2 day", "3 day", "4 day", "to day", "too day"};
        String[] morAfterEve = new String[]{"morning", "afternoon", "evening"};

        if (haveAppointmentAsked || haveFileAsked) {
            Log.d(TAG, "hasappointmentAsked or havefileAsked: " + haveAppointmentAsked + haveFileAsked);
            if (TextUtils.equals(result, "yes"))
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no"))
                rightAnswer("no");
            else
                wrongAnswer("");
        } else if (tellNameAsked) {
            Log.d(TAG, "tellNameAsked: " + tellNameAsked);
            userName = result;
            if (TextUtils.equals(result, "john"))
                rightAnswer(userName);
            else
                wrongAnswer("");
        } else if (selectIdAsked) {
//            micImageView.setVisibility(View.INVISIBLE);

        } else if (whichDayAsked) {
            for (String day : daysOfWeek) {
                if (TextUtils.equals(result, day)) {
                    daySelected = day;
                    rightAnswer(day);
                    return;
                }
            }
            wrongAnswer("day");
        } else if (morAfterEveAsked) {
            for (String mAE : morAfterEve) {
                if (TextUtils.equals(result, mAE)) {
                    mAESelected = mAE;
                    rightAnswer(mAE);
                    return;
                }
            }
            wrongAnswer("mAE");
        } else if (whatTimeAsked) {
            for (String time : timeOfDay) {
                if (result.contains(time)) {
                    timeSelected = time + " o'clock";
                    rightAnswer(time);
                    return;
                }
            }
            wrongAnswer("time");
        } else if (haveInsuranceAsked) {
            Log.d(TAG, "have insurance asked");
            if (TextUtils.equals(result, "yes"))
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no"))
                rightAnswer("no");
            else
                wrongAnswer("");
        } else if (bodyPartHighlighted) {
            Log.d(TAG, "bodyPartHighlighted");
            if (bodyProblem == 0 && (result.equals("head ache") || result.equals("headache"))) {
                rightAnswer("headache");
                return;
            } else if (bodyProblem == 1 && (result.equals("chest pain") || result.equals("chestpain"))) {
                rightAnswer("chest pain");
                return;
            } else if (bodyProblem == 2 && (result.equals("stomach ache") || result.equals("stomachache"))) {
                rightAnswer("stomachache");
                return;
            } else
                wrongAnswer("bodyProblem");
        } else if(forHowLongAsked) {
            if (TextUtils.equals(result, "1 day") || TextUtils.equals(result, "one day") || TextUtils.equals(result, "oneday"))
                rightAnswer("one day");
            else
                wrongAnswer("one day");
        } else if(haveFeverAsked) {
            Log.d(TAG, "haveFeverAsked");
            if (randFever == 0 || randFever == 2) {
                if (TextUtils.equals(result, "yes"))
                    rightAnswer("haveFeverAsked");
                else
                    wrongAnswer("haveFeverAsked");
            } else if (randFever == 1) {
                if (TextUtils.equals(result, "no"))
                    rightAnswer("haveFeverAsked");
                else
                    wrongAnswer("haveFeverAsked");
            }
        } else {
            for (String docName : doctorNames) {
                if (TextUtils.equals(result, docName)) {
                    doctorNameSelected = docName;
                    rightAnswer(docName);
                    return;
                }
            }
            wrongAnswer("doc");
        }
    }

    private void wrongAnswer(String check) {
        if (check.equals("doc"))
            Toast.makeText(this, "There is no doctor available with that name", Toast.LENGTH_SHORT).show();
        else if (check.equals("day"))
            Toast.makeText(this, "There is no such day", Toast.LENGTH_SHORT).show();
        else if (check.equals("bodyProblem"))
            Toast.makeText(this, "This is not the right problem", Toast.LENGTH_SHORT).show();
        else if (check.equals("haveFeverAsked") ) {
            if (randFever == 1)
                Toast.makeText(this, "You have fever, please try again.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "You don't have fever, please try again.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "That was not the right answer", Toast.LENGTH_SHORT).show();
    }

    private void rightAnswer(String check) {
        Log.d(TAG, "rightAnswer approached!");
        if (haveAppointmentAsked) {
            haveAppointmentAsked = false;
            if (check.equals("yes"))
                haveAppointment = true;
            else if (check.equals("no"))
                haveAppointment = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("May I have your name?");
            textToSpeech.speak("May I have your name?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            tellNameAsked = true;
        } else if (tellNameAsked) {
            tellNameAsked = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            if (haveAppointment) {
                profileTextView.setText("Do you have a file?");
                textToSpeech.speak("Do you have a file?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                haveFileAsked = true;
            } else {
                profileTextView.setText("Which day?");
                textToSpeech.speak("Which day?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                whichDayAsked = true;
            }
        } else if (haveFileAsked) {
            haveFileAsked = false;
            enableMic = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("May i have your ID?");
            textToSpeech.speak("May i have your ID?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            selectIdAsked = true;
        } else if (whichDayAsked) {
            whichDayAsked = false;
            extraImageView.setImageResource(R.drawable.timing);
            extraImageView.setVisibility(View.VISIBLE);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("Morning, afternoon or evening?");
            textToSpeech.speak("Morning, afternoon or evening?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            morAfterEveAsked = true;
        } else if (morAfterEveAsked) {
            morAfterEveAsked = false;
            extraImageView.setVisibility(View.GONE);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("What time?");
            textToSpeech.speak("What time?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            whatTimeAsked = true;
        } else if (whatTimeAsked) {
            whatTimeAsked = false;
            extraTextView.setText("Your appointment with " + doctorNameSelected + " is on " + daySelected + " at " + timeSelected);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileImageView.setVisibility(View.INVISIBLE);
            profileTextView.setText("");
            new CountDownTimer(4000, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    extraTextView.setText("");
                    profileImageView.setVisibility(View.VISIBLE);
                    profileTextView.setText("Do you have a file?");
                    textToSpeech.speak("Do you have a file?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                    haveFileAsked = true;
                }
            }.start();
        } else if (haveInsuranceAsked) {
            haveInsuranceAsked = false;
            enableMic = false;
            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
            if (check.equals("yes")) {
                profileTextView.setText("May i have your insurance card?");
                textToSpeech.speak("May i have your insurance card?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                selectInsuranceAsked = true;
            } else if (check.equals("no")) {
                profileTextView.setText("it's $15");
                textToSpeech.speak("it's $15", TextToSpeech.QUEUE_FLUSH, params, "12345");
                selectCashAsked = true;
            }
        } else if (bodyPartHighlighted) {
            bodyPartHighlighted = false;
            extraAnimation.setVisibility(View.GONE);
            extraAnimation.loop(false);
            extraAnimation.setAnimation("thermometer.json");
            profileImageView.setImageResource(R.drawable.doctor_in_office);
            profileTextView.setText("For how long?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_for_how_long);
            mediaPlayer.start();
            forHowLongAsked = true;
        } else if (forHowLongAsked) {
            forHowLongAsked = false;
            profileImageView.setImageResource(R.drawable.doctor_in_office);
            profileTextView.setText("Do you have fever?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_do_you_have_fever);
            mediaPlayer.start();
            extraAnimation.setVisibility(View.VISIBLE);
            extraAnimation.playAnimation(1, 17);
//            randFever = new Random().nextInt(3);
//            switch (randFever) {
//                case 0:
//                    Log.d(TAG, "fever");
//                    extraAnimation.playAnimation(18, 18);
//                    break;
//                case 1:
//                    Log.d(TAG, "no fever");
//                    extraAnimation.playAnimation(21, 21);
//                    break;
//                case 2:
//                    Log.d(TAG, "fever");
//                    extraAnimation.playAnimation(23, 23);
//                    break;
//            }
            haveFeverAsked = true;
        } else if (haveFeverAsked) {
            Log.d(TAG, "HaveFeverInRightAnswer");
            haveFeverAsked = false;
            startStage(FOURTH_STAGE);
        } else {
            int randomNum = random.nextInt(1);
            switch (randomNum) {
                case 0:
                    profileImageView.setImageResource(R.drawable.nurse_reception);
                    profileTextView.setText("Do you have an appointment?");
                    extraImageView.setVisibility(View.GONE);
                    textToSpeech.speak("Do you have an appointment?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                    haveAppointmentAsked = true;
                    break;
                case 1:

                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_start:
                extraAnimation.setVisibility(View.GONE);
                extraImageView.setVisibility(View.GONE);
                profileImageView.setVisibility(View.INVISIBLE);
                profileTextView.setVisibility(View.INVISIBLE);
                micWalletContainer.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.GONE);
                mainImageView.setImageResource(R.drawable.reception);
                enableMic = false;
                startStage(FIRST_STAGE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.mic_imageView:
                if (enableMic && !textToSpeech.isSpeaking()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            view.setPressed(true);
                            if (speechRecognizer.isRecognitionAvailable(this) && !isRecording) {
                                Log.d(TAG, "recognition available");
                                initSpeechRecognition();
                                speechRecognizer.startListening(speechIntent);
                                micImageView.setImageResource(R.drawable.mic_busy);
                                isRecording = true;
                            } else {
                                Log.d(TAG, "recognition not available");
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            view.setPressed(false);
                            if (isRecording) {
                                speechRecognizer.stopListening();
                                micImageView.setImageResource(R.drawable.mic);
                                isRecording = false;
                            }
                            break;
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int i) {
        micImageView.setImageResource(R.drawable.mic);
        speechRecognizer.cancel();
        isRecording = false;
        String errorMessage = getErrorMessage(i);
        if (errorToast != null) {
            errorToast.cancel();
        }
        errorToast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle bundle) {
        String result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        checkResult(result);
        Log.d(TAG, result);
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
