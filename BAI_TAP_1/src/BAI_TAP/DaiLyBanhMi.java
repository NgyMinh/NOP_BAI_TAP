package BAI_TAP;

class DaiLyBanhMi {
	private int soLuongBanhMi = 0;
	private final int SUC_CHUA = 5; // SỨC CHỨA

	public synchronized void sanXuat() throws InterruptedException {
		while (soLuongBanhMi >= SUC_CHUA) {
			System.out.println("Đại lý đầy bánh mì, Nhà sản xuất chờ...");
			wait(); 
		}
		soLuongBanhMi++;
		System.out.println("Nhà sản xuất làm thêm bánh mì. Tổng số: " + soLuongBanhMi);
		notifyAll(); 
	}

	public synchronized void muaBanhMi() throws InterruptedException {
		while (soLuongBanhMi == 0) {
			System.out.println("Hết bánh mì, Người mua chờ...");
			wait(); 
		}
		soLuongBanhMi--;
		System.out.println("Người mua lấy 1 bánh mì. Còn lại: " + soLuongBanhMi);
		notifyAll(); //
	}
}

class NhaSanXuat extends Thread {
	private final DaiLyBanhMi daiLy;

	public NhaSanXuat(DaiLyBanhMi daiLy) {
		this.daiLy = daiLy;
	}

	@Override
	public void run() {
		try {
			while (true) {
				daiLy.sanXuat();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class NguoiMua extends Thread {
	private final DaiLyBanhMi daiLy;

	public NguoiMua(DaiLyBanhMi daiLy) {
		this.daiLy = daiLy;
	}

	@Override
	public void run() {
		try {
			while (true) {
				daiLy.muaBanhMi();
				Thread.sleep(1500); 
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DaiLyBanhMi daiLy = new DaiLyBanhMi();
		NhaSanXuat nhaSanXuat = new NhaSanXuat(daiLy);
		NguoiMua nguoiMua = new NguoiMua(daiLy);

		nhaSanXuat.start();
		nguoiMua.start();
	}
}
