package hbu.example.foodtrip_01;

public class Restaurant {
	private String name = "";
	private String address = "";

	public String getName() {
		return (name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return (address);
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	private String type = "";

	public String getType() {
		return (type);
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	//Add Items to ListView
	public String toString() {
		return getName()+ " , " + getAddress()	+ " , " + getType();

	}

}
	
	
	

