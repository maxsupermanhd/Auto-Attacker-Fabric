package pepe.frog.autoattacker;

class Configuration {
	private long delay = 1000;
	private long randomness = 20;
	
	public Configuration(long d, long r) {
		this.delay = d;
		this.randomness = r;
	}
	
	public long GetDelay() {return this.delay;}
	public long GetRandom() {return this.randomness;}
	public void SetDelay(long d) {this.delay = d;}
	public void SetRandom(long r) {this.randomness = r;}
}