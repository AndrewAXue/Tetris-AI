package Framework;

public class Population {
	public chromosome pop[];
	int size;
	
	// Creates a list of size chromosomes
	public Population(int tempsize){
		pop = new chromosome[tempsize];
		size=tempsize;
	}
	
	// Creates a random chromosome for the each chromosome of the population with numprop properties bounded from lowbound to upbound
	public void randomize(int numprop, int lowbound, int upbound){
		for (int i=0;i<size;i++){
			chromosome temp = new chromosome(numprop);
			temp.randomize(lowbound,upbound);
			pop[i]=temp;
		}
	}
	
}
