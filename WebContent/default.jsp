<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .accordion-container {
            width: 200px; /* 원하는 너비로 설정ㅇ */
            margin-left: 0; /* 왼쪽 정렬 */
        }
        .clickable-item {
            display: block;
            padding: 10px;
            margin-bottom: 5px;
            background-color: #e9ecef;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            text-decoration: none;
            color: #000;
        }
        .clickable-item:hover {
            background-color: #d8d8d8;
        }
    </style>
</head>
<body>
    <div class="accordion-container">
        <div class="accordion" id="accordionPanelsStayOpenExample">
            <!-- 견적서 -->
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                       견적서
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
                    <div class="accordion-body">
                        <a href="controller?action=quoteView" class="clickable-item" id="quoteView">견적서 조회</a>
                        <a href="controller?action=quoteInput" class="clickable-item" id="quoteInput">견적서 입력</a>
                        <a href="controller?action=quoteStatus" class="clickable-item" id="quoteStatus">견적서 현황</a>
                        <a href="controller?action=unorderedStatus" class="clickable-item" id="unorderedStatus">미주문 현황</a>
                    </div>
                </div>
            </div>
            <!-- 주문서 -->
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                       주문서
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                    <div class="accordion-body">
                        <a href="controller?action=orderView" class="clickable-item" id="orderView">주문서 조회</a>
                        <a href="controller?action=orderInput" class="clickable-item" id="orderInput">주문서 입력</a>
                        <a href="controller?action=orderStatus" class="clickable-item" id="orderStatus">주문서 현황</a>
                        <a href="controller?action=orderShipping" class="clickable-item" id="orderShipping">주문서 출고처리</a>
                        <a href="controller?action=unsoldStatus" class="clickable-item" id="unsoldStatus">미판매현황</a>
                    </div>
                </div>
            </div>
            <!-- 판매 -->
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                        판매
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse">
                    <div class="accordion-body">
                        <a href="controller?action=salesView" class="clickable-item" id="salesView">판매 조회</a>
                        <a href="controller?action=salesInput" class="clickable-item" id="salesInput">판매 입력</a>
                        <a href="controller?action=salesInput2" class="clickable-item" id="salesInput2">판매 입력2</a>
                        <a href="controller?action=salesPriceChange" class="clickable-item" id="salesPriceChange">판매단가 일관변경</a>
                        <a href="controller?action=salesStatus" class="clickable-item" id="salesStatus">판매 현황</a>
                        <a href="controller?action=paymentView" class="clickable-item" id="paymentView">결제 내역 조회</a>
                        <a href="controller?action=paymentComparison" class="clickable-item" id="paymentComparison">결제 내역 자료비교</a>
                    </div>
                </div>
            </div>
            <!-- 출하지시서 -->
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseFour" aria-expanded="false" aria-controls="panelsStayOpen-collapseFour">
                       출하지시서
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseFour" class="accordion-collapse collapse">
                    <div class="accordion-body">
                        <a href="controller?action=shipmentOrderView" class="clickable-item" id="shipmentOrderView">출하지시서 조회</a>
                        <a href="controller?action=shipmentOrderInput" class="clickable-item" id="shipmentOrderInput">출하지시서 입력</a>
                        <a href="controller?action=shipmentOrderStatus" class="clickable-item" id="shipmentOrderStatus">출하지시서 현황</a>
                    </div>
                </div>
            </div>
            <!-- 출하 -->
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseFive" aria-expanded="false" aria-controls="panelsStayOpen-collapseFive">
                       출하
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseFive" class="accordion-collapse collapse">
                    <div class="accordion-body">
                        <a href="controller?action=shipmentView" class="clickable-item" id="shipmentView">출하 조회</a>
                        <a href="controller?action=shipmentInput" class="clickable-item" id="shipmentInput">출하 입력</a>
                        <a href="controller?action=shipmentStatus" class="clickable-item" id="shipmentStatus">출하 현황</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
