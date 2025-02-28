package BAI_TAP;

class NhaGa {
	private int choNgoi = 5; 
	private int hanhKhachDoi = 0;

	public synchronized void hanhKhachDen() throws InterruptedException {
		hanhKhachDoi++;
		System.out.println("Hành khách đến, tổng số chờ: " + hanhKhachDoi);
		notifyAll(); 
	}

	public synchronized void tauDen() throws InterruptedException {
		while (hanhKhachDoi == 0) {
			System.out.println("Tàu chờ hành khách...");
			wait();
		}

		int lenTau = Math.min(hanhKhachDoi, choNgoi);
		hanhKhachDoi -= lenTau;

		System.out.println("Tàu đến, đón " + lenTau + " hành khách. Còn lại: " + hanhKhachDoi);
		Thread.sleep(2000);
		System.out.println("Tàu rời ga!");
	}
}

class HanhKhach extends Thread {
	private final NhaGa nhaGa;

	public HanhKhach(NhaGa nhaGa) {
		this.nhaGa = nhaGa;
	}

	@Override
	public void run() {
		try {
			while (true) {
				nhaGa.hanhKhachDen();
				Thread.sleep((int) (Math.random() * 2000)); // Hành khách đến ngẫu nhiên
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class TauHoa extends Thread {
	private final NhaGa nhaGa;

	public TauHoa(NhaGa nhaGa) {
		this.nhaGa = nhaGa;
	}

	@Override
	public void run() {
		try {
			while (true) {
				nhaGa.tauDen();
				Thread.sleep(5000); 
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		NhaGa nhaGa = new NhaGa();
		TauHoa tau = new TauHoa(nhaGa);
		HanhKhach hanhKhach = new HanhKhach(nhaGa);

		tau.start();
		hanhKhach.start();
	}
}

