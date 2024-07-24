window.onload = function () {
  tizen.power.request('SCREEN', 'SCREEN_NORMAL');
  var firebaseConfig = {
    apiKey: '__REDACTED__',
    authDomain: 'fitness-b6467.firebaseapp.com',
    databaseURL: 'https://fitness-b6467-default-rtdb.firebaseio.com',
    projectId: 'fitness-b6467',
    storageBucket: 'fitness-b6467.appspot.com',
    messagingSenderId: '29836529998',
    appId: '1:29836529998:web:4d5783781cf98fb964ce1f',
    measurementId: 'G-4H7M8FM1RK',
  };

  firebase.initializeApp(firebaseConfig);
  // TODO:: Do your initialization job

  // add eventListener for tizenhwkey
  document.addEventListener('tizenhwkey', function (e) {
    if (e.keyName == 'back')
      try {
        tizen.application.getCurrentApplication().exit();
      } catch (ignore) {}
  });

  // Sample code
  function uploadToFirebase(heartRate) {
    var database = firebase.database();
    var userRef = database.ref('users').child('Vishal');
    userRef.set({
      heartRate: heartRate,
    });
  }
  function onSuccess() {
    var heartRateElement = document.getElementById('textbox');
    function onchangedCB(hrmInfo) {
      heartRateElement.textContent = 'Heart rate: ' + hrmInfo.heartRate;
      uploadToFirebase(hrmInfo.heartRate);
      //console.log("heart rate:" + hrmInfo.heartRate);
      // tizen.humanactivitymonitor.stop('HRM'); // Commented out to keep monitoring continuously
    }

    // Start heart rate monitoring every second
    setInterval(function () {
      tizen.humanactivitymonitor.start('HRM', onchangedCB);
    }, 1000);
  }

  function onError(e) {
    console.log('error ' + JSON.stringify(e));
  }

  tizen.ppm.requestPermission('http://tizen.org/privilege/healthinfo', onSuccess, onError);
};
