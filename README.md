
ePOS for android app
===================

#Nghiệp vụ phần Restaurant:

-	Xếp bàn: Khi bấm vào bàn, và bấm xếp bàn thì bàn đó sẽ được hiểu là đang có khách ăn uống ở đó và thực hiện add items. Thanh toán như bình thường.
Về thông tin bàn đã được xếp đó như: Tên KH, đồ ăn uống, giá tiền sẽ được lưu vào database sau khi bấm vào nút LƯU. Để nhân viên khác có thể bấm nút REFRESH trạng thái bàn và thấy thông tin bàn đó để thanh toán, không nhất thiết chỉ nhân viên trực tiếp xếp bàn mới được thanh toán.
( Sau khi thanh toán xong, table đó có chứa trường order_id sẽ set order_id = 0y)

-	Đặt bàn: Khi bấm vào bàn, và bấm vào Đặt Bàn thì bàn đó được hiểu là có khách đang đặt bàn đó rồi ( có thể là gọi điện thoại đặt trước và sẽ đến sau ). Khi nào khách đến thì bấm vào bàn đã được đặt đó và XẾP BÀN thì lại như ở trên.
-	Gộp bàn: Đang hoàn thiện.

#	Nghiệp vụ phần Shop

Chọn KH, add items, sau đó nếu THANH TOÁN luôn thì sẽ gửi thông tin hóa đơn đó lên server luôn.
Còn nếu bấm vào LƯU TẠM thì nó sẽ lưu thông tin hóa đơn đó lên server để sau khi load lại, tất cả thông tin hóa đơn đó sẽ điền lại vào bảng để mua hàng tiếp.

•	Nghiệp vụ phần Giao diện bán hàng:

Mỗi khi nhân viên login thành công, sẽ thực hiện điều đầu tiên đó là LOAD giao diện mặc định của nhà hàng đó. 

VD nhà hàng đó có 3 loại giao diện ( server sẽ trả về tên từng giao diện )
-	Thông thường
-	Tết
-	Noel
