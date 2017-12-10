package com.example.user.myapplicationhospital;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

//http://www.fromtexttospeech.com/

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FIRST_STAGE = "first";
    private static final String SECOND_STAGE = "second";
    private BottomSheetBehavior bottomSheetBehavior;
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
    private TextView profileTextView;
    private TextView extraTextView;
    private MediaPlayer mediaPlayer;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private String doctorNameSelected;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomSheet = (FrameLayout) findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        profileTextView = (TextView) findViewById(R.id.profile_textView);
        extraTextView = (TextView) findViewById(R.id.extra_textView);
        profileImageView = (ImageView) findViewById(R.id.profile_imageView);
        extraImageView = (ImageView) findViewById(R.id.extra_imageView);
        micImageView = (ImageView) findViewById(R.id.mic_imageView);
        micImageView.setOnTouchListener(this);
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
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        speechIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    private void startStage(String stage) {
        switch (stage) {
            case FIRST_STAGE:
                firstStage();
                break;
            case SECOND_STAGE:
                secondStage();
                break;

        }
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
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void firstStage() {
        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 17:
                        profileImageView.setVisibility(View.VISIBLE);
                        profileTextView.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("How can i help you");
                        textToSpeech.speak("How can i help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                        break;
                    case 12:
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("I would like to see a doctor");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_see_doc);
                        mediaPlayer.start();
                        break;
                    case 7:
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("Which doctor?");
                        textToSpeech.speak("Which doctor?", TextToSpeech.QUEUE_FLUSH, null, null);
                        extraImageView.setVisibility(View.VISIBLE);
                        enableMic = true;
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
        if (selectCashAsked) {
            Log.d(TAG, "cash");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            selectCashAsked = false;
        } else if (selectIdAsked) {
            Log.d(TAG, "id");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(SECOND_STAGE);
            selectIdAsked = false;
        } else if (selectInsuranceAsked) {
            Log.d(TAG, "insurance");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            selectInsuranceAsked = false;
        } else if (selectPrescriptionAsked) {
            Log.d(TAG, "prescription");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            selectPrescriptionAsked = false;
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
        String[] doctorNames = new String[]{"dr jack", "any doctor", "dr ahmad", "dr nancy", "dr ahmed", "doctor nancy", "doctor ahmad", "doctor ahmed", "doctor jack", "any doctor"};
        String[] daysOfWeek = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
        String[] timeOfDay = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        String[] whatIsTheProblem = new String[]{"I have headache", "I have stomachache", "I have chest pain", "headache", "stomachache", "chest pain", "stomach ache", "head ache", "chestpain"};
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
            for (String problem : whatIsTheProblem) {
                if (TextUtils.equals(result, problem)) {
                    rightAnswer(problem);
                    return;
                }
            }
            wrongAnswer("problem");
        } else if(forHowLongAsked) {
            if (TextUtils.equals(result, "1 day") || TextUtils.equals(result, "one day") || TextUtils.equals(result, "oneday"))
                rightAnswer("one day");
            else
                wrongAnswer("one day");
        } else if(haveFeverAsked) {
            Log.d(TAG, "haveFeverAsked");
            if (TextUtils.equals(result, "yes") || TextUtils.equals(result, "no"))
                rightAnswer("haveFeverAsked");
            else
                wrongAnswer("haveFeverAsked");
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
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("May i have your ID?");
            textToSpeech.speak("May i have your ID?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            selectIdAsked = true;
        } else if (whichDayAsked) {
            whichDayAsked = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("Morning, afternoon or evening?");
            textToSpeech.speak("Morning, afternoon or evening?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            morAfterEveAsked = true;
        } else if (morAfterEveAsked) {
            morAfterEveAsked = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("What time?");
            textToSpeech.speak("What time?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            whatTimeAsked = true;
        } else if (whatTimeAsked) {
            whatTimeAsked = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileImageView.setVisibility(View.INVISIBLE);
            profileTextView.setText("");
            extraTextView.setText("Your appointment with " + doctorNameSelected + " is on " + daySelected + " at " + timeSelected);
            new CountDownTimer(3000, 1000) {

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
            micImageView.setVisibility(View.INVISIBLE);
            if (check.equals("yes")) {
                Log.d(TAG, "have insurance");
                textToSpeech.speak("May i have your insurance card?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                selectInsuranceAsked = true;
            } else if (check.equals("no")) {
                Log.d(TAG, "don't have insurance");
                textToSpeech.speak("it's $15", TextToSpeech.QUEUE_FLUSH, params, "12345");
//                selectcashAsked = true;
            }
        } else if (bodyPartHighlighted) {
            bodyPartHighlighted = false;
            textToSpeech.speak("For how long?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            forHowLongAsked = true;
        } else if (forHowLongAsked) {
            forHowLongAsked = false;
            textToSpeech.speak("Do you have fever?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            haveFeverAsked = true;
        } else if (haveFeverAsked) {
            Log.d(TAG, "HaveFeverInRightAnswer");
            haveFeverAsked = false;
//            haveFeverAnswered = true;
//            fifthTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    runAnimation();
//                }
//            }, 2000, 4000);
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
                extraImageView.setVisibility(View.GONE);
                profileImageView.setVisibility(View.INVISIBLE);
                profileTextView.setVisibility(View.INVISIBLE);
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
                            if (!isRecording) {
                                initSpeechRecognition();
                                if (SpeechRecognizer.isRecognitionAvailable(MainActivity.this)) {
                                    speechRecognizer.startListening(speechIntent);
                                    micImageView.setImageResource(R.drawable.mic_busy);
                                    isRecording = true;
                                }
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
        micImageView.setImageResource(R.drawable.mic);
        isRecording = false;
    }

    @Override
    public void onError(int i) {
        String errorMessage = getErrorMessage(i);
        isRecording = false;
        speechRecognizer.destroy();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle bundle) {
        String result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        checkResult(result);
        Log.d(TAG, result);
        speechRecognizer.destroy();
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
