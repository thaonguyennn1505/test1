USE master
GO
CREATE DATABASE STORE
GO
USE STORE
GO
CREATE TABLE USERS(
	id_user VARCHAR(50) PRIMARY KEY,
	sdt VARCHAR(10) NOT NULL,
	hinh VARCHAR(255) NULL,
	hoten NVARCHAR(50) NOT NULL,
	matkhau VARCHAR(50) NOT NULL,
	vaitro BIT NOT NULL
)
GO
CREATE TABLE LOAI(
	id_loai INT IDENTITY(1,1) PRIMARY KEY,
	ten_loai NVARCHAR(255) NOT NULL
)
GO
CREATE TABLE SANPHAM(
	id_sanpham INT IDENTITY(1,1) PRIMARY KEY,
	ten_sanpham NVARCHAR(255) NOT NULL,
	soluong INT NOT NULL,
	hinh VARCHAR(255) NULL,
	mota NVARCHAR(MAX) NOT NULL,
	motangan NVARCHAR(MAX) NULL,
	gia INT NOT NULL,
	giamgia INT NOT NULL,
	ngaytao DATE NOT NULL,
	id_loai INT FOREIGN KEY REFERENCES Loai(id_loai)
)

GO
CREATE TABLE HOADON(
	id_hoadon INT IDENTITY(1,1) PRIMARY KEY,
	ngaytao DATE NOT NULL,
	trangthai VARCHAR(30) NOT NULL,
	diachi NVARCHAR(50) NOT NULL,
	giaohang NVARCHAR(MAX) NULL,
	id_user VARCHAR(50) FOREIGN KEY REFERENCES Users(id_user)
)
GO
CREATE TABLE HOADONCHITIET(
	id_hoadon INT,
	id_sanpham INT,
	soluong INT NOT NULL,
	PRIMARY KEY (id_hoadon, id_sanpham),
    FOREIGN KEY (id_hoadon) REFERENCES HoaDon(id_hoadon),
    FOREIGN KEY (id_sanpham) REFERENCES SanPham(id_sanpham)
)
GO
CREATE TABLE GIOHANG(
	id_giohang INT IDENTITY(1,1) PRIMARY KEY,
	id_user VARCHAR(50) FOREIGN KEY REFERENCES Users(id_user)
)
GO
CREATE TABLE GIOHANGCHITIET(
	id_giohang INT,
	id_sanpham INT,
	soluong INT NOT NULL,
	PRIMARY KEY (id_giohang, id_sanpham),
    FOREIGN KEY (id_giohang) REFERENCES GioHang(id_giohang),
    FOREIGN KEY (id_sanpham) REFERENCES SanPham(id_sanpham)
)
GO

-- Thêm dữ liệu vào bảng USERS (Khách hàng & Nhân viên)
INSERT INTO USERS (id_user, sdt, hinh, hoten, matkhau, vaitro)
VALUES 
('kh01', '0987654321', NULL, N'Nguyễn Văn An', 'pass123', 0),
('kh02', '0976543210', NULL, N'Trần Thị Bình', 'pass456', 0),
('nv01', '0965432109', NULL, N'Phạm Văn Cường', 'admin123', 0);

-- Thêm dữ liệu vào bảng LOAI (Loại thuốc)
INSERT INTO LOAI (ten_loai)
VALUES 
(N'Thuốc giảm đau'),
(N'Thuốc kháng sinh'),
(N'Thực phẩm chức năng');

-- Thêm dữ liệu vào bảng SANPHAM (Sản phẩm - thuốc)
INSERT INTO SANPHAM (ten_sanpham, soluong, hinh, mota, motangan, gia, giamgia, ngaytao, id_loai)
VALUES 
(N'Paracetamol 500mg', 100, NULL, N'Giảm đau, hạ sốt', N'Thuốc giảm đau', 50000, 5000, '2025-02-27', 1),
(N'Amoxicillin 250mg', 50, NULL, N'Thuốc kháng sinh phổ rộng', N'Thuốc kháng sinh', 70000, 7000, '2025-02-27', 2),
(N'Vitamin C 500mg', 200, NULL, N'Hỗ trợ tăng cường đề kháng', N'Thực phẩm chức năng', 120000, 10000, '2025-02-27', 3);

-- Thêm dữ liệu vào bảng HOADON (Đơn hàng)
INSERT INTO HOADON (ngaytao, trangthai, diachi, giaohang, id_user)
VALUES 
('2025-02-27', 'Chờ xác nhận', N'123 Nguyễn Trãi, Hà Nội', NULL, 'kh01'),
('2025-02-26', 'Đang giao', N'456 Lê Văn Sỹ, TP HCM', N'Giao trước 18h', 'kh02');

-- Thêm dữ liệu vào bảng HOADONCHITIET (Chi tiết hóa đơn)
INSERT INTO HOADONCHITIET (id_hoadon, id_sanpham, soluong)
VALUES 
(1, 2, 2), -- 2 hộp Paracetamol
(1, 3, 1), -- 1 hộp Vitamin C
(2, 4, 1); -- 1 hộp Amoxicillin

-- Thêm dữ liệu vào bảng GIOHANG (Giỏ hàng của khách)
INSERT INTO GIOHANG (id_user)
VALUES 
('kh01'),
('kh02');

-- Thêm dữ liệu vào bảng GIOHANGCHITIET (Chi tiết giỏ hàng)
INSERT INTO GIOHANGCHITIET (id_giohang, id_sanpham, soluong)
VALUES 
(2, 2, 2), -- 2 hộp Paracetamol trong giỏ hàng
(2, 3, 1), -- 1 hộp Amoxicillin
(3, 4, 3); -- 3 hộp Vitamin C trong giỏ hàng

Select * FROM GIOHANGCHITIET;
Select * FROM GIOHANG;
Select * FROM HOADONCHITIET;
Select * FROM HOADON;
Select * FROM SANPHAM;
Select * FROM LOAI;
Select * FROM USERS;
