# virtual-piano-bot-with-soul
to give a piano bot a soul

"Music is the space between the notes" - Claude DeBussy

Cái hồn của âm nhạc không nằm ở việc đánh chính xác từng nốt mà nó nằm ở thời gian đánh các nốt.
Cùng một bài hát, tiết tấu nhanh mạnh và tiết tấu chậm nhẹ đem lại các cung bậc cảm xúc khác nhau cho người nghe.
Đây là khả năng độc quyền của con người.

Bot thì sao?
Bot có thể đánh chính xác từng note nhạc và không mắc bất cứ lỗi nào như con người.
Đó là một điều tuyệt vời, nhưng chính điều đó ngăn cách Bot và Con Người. 
Chúng ta mắc sai lầm, chúng ta thể hiện cảm xúc.

Tôi muốn bot cũng có thể có được điều đó.
Dù chỉ là tái tạo lại sai lầm, tái tạo lại cảm xúc của con người.
Mục tiêu này không phải là mục tiêu tối ưu hóa độ chính xác.
Mục tiêu này là tái tạo linh hồn cho bot.

# I. Cách hoạt động
Hoạt động được chia ra làm 2 phần chính:
1. Chức năng đánh đàn
2. Xác định thời gian nghỉ giữa các nốt.

1. Chức năng đánh đàn
Đây là phần dễ nhất, 2 tiếng đồng hồ tập trung viết code là ai cũng có thể làm được

Tôi sử dụng phần mềm Virtual Piano để đánh đàn
Tôi sử dụng phần mềm AutoHotKey để tự động đánh phím
Tôi viết code chuyển bản nhạc từ file txt thành các lệnh AutoHotKey hiểu

Tất cả bot đánh đàn Virtual Piano trên git hub đều dừng tại bước này.
Tôi muốn đi xa hơn nữa trên sứ mệnh đem lại linh hồn cho bot.

2. Xác định thời gian nghỉ giữa các nốt
Do hạn chế về kiến thức piano, tôi chỉ phân biệt ra ba loại khoảng thời gian dừng:
+ Ngắn 
+ Trung Bình: Đây là thời gian dừng mặc định giữa các nốt.
+ Dài

Một chuỗi các đoạn dừng ngắn sẽ tạo ra bài hát có tiết tấu nhanh, dồn dập.
Một chuỗi các đoạn dừng dài sẽ tạo ra một bài hát buồn

Hiện tại tôi đang set up thời gian dừng bằng tay.
Người dùng phải định nghĩa (thời gian dừng ngắn, và thời gian dừng trung bình)
thời gian dừng dài sẽ là (thời gian trung bình + thời gian dừng ngắn) 

Tôi muốn công việc này được thực hiện bằng machine learning.
Là sinh viên năm 2 CNTT Bách Khoa, kiến thức của tôi về mảng này còn rất hạn chế và đang được cải thiện dần.

Tôi cũng chưa có bất cứ ý tưởng gì cho áp dụng machine learning.
Tôi rất mong các ý kiến đóng góp và sự chỉ bảo có thể giúp tôi đem lại linh hồn cho bot.

# II. Sử dụng
Do tôi tập trung chủ yếu vào phần "xác định thời gian nghỉ giữa các nốt" nên tôi không tạo ra bất cứ GUI nào.

Bước 1: cài đặt bộ cài AutoHotKey.exe
Bước 2: chuyển bản nhạc muốn tự động chơi vào thư mục "Online piano sheet" dưới định dạng .txt
Bước 3: chạy file java TXT2AHK.java bằng cmd hoặc bằng bất cứ IDE nào
Bước 4: điền các thông tin mà TXT2AHK.java báo
Bước 5: vào thư mục chạy file .ahk tương ứng
Bước 6: Bật Virtual Piano trong tệp Virtual Piano
Bước 7: Kiểm tra Virtual Piano đã chạy ngon lành bằng cách ấn thử vài nút
Bước 8: ấn F1.
