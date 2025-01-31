
    const managerId = document.getElementById("info").dataset
        .managerId;

    console.log("managerId: " + managerId);
    // SSE 연결 설정
    const eventSource = new EventSource(`/sse-connect/${managerId}`);

    // 연결이 열렸을 때
    eventSource.onopen = function(event) {
        console.log('SSE 연결이 열렸습니다.');
    };

    // 메시지를 수신했을 때
    eventSource.onmessage = function(event) {
        console.log('알림 수신:', event.data);
        showNotification(event.data);
    };

    // 특정 이벤트 이름으로 메시지 수신 (예: 'alarm')
    eventSource.addEventListener('alarm', function(event) {
        console.log('알림 수신 (alarm):', event.data);
        showNotification(event.data);
    });

    // 에러가 발생했을 때
    eventSource.onerror = function(event) {
        console.error('SSE 에러 발생:', event);
        // 연결을 닫고 재연결 로직을 추가할 수 있습니다.
        eventSource.close();
        // 재연결 시도 예시 (5초 후)
        setTimeout(function() {
            reconnectSSE();
        }, 1500);
    };

    // 알림 표시 함수
    function showNotification(message) {
        const notificationDiv = document.getElementById('notification');
        notificationDiv.querySelector('.contents').innerText = message;

        alert(message);
        if (confirm(message)) {
            // todo 모달 창으로 처리할지, 단순 알람으로 처리할 지 추후 상황 보고 결정
        }
        notificationDiv.style.display = 'block';

        // // 5초 후에 알림 숨기기
        // setTimeout(function() {
        //     notificationDiv.style.display = 'none';
        // }, 5000);
    }

    // SSE 재연결 함수
    function reconnectSSE() {
        console.log('SSE 재연결을 시도합니다.');
        const newEventSource = new EventSource(`/sse-connect/${managerId}`);

        newEventSource.onopen = function(event) {
            console.log('SSE 연결이 다시 열렸습니다.');
        };

        newEventSource.onmessage = function(event) {
            console.log('알림 수신:', event.data);
            showNotification(event.data);
        };

        newEventSource.addEventListener('alarm', function(event) {
            console.log('알림 수신 (alarm):', event.data);
            showNotification(event.data);
        });

        newEventSource.onerror = function(event) {
            console.error('SSE 에러 발생:', event);
            newEventSource.close();
            setTimeout(function() {
                reconnectSSE();
            }, 1500);
        };
    }