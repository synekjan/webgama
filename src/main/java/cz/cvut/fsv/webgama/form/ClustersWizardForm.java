package cz.cvut.fsv.webgama.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Input;

public class ClustersWizardForm {

	@Valid
	private List<Cluster> clusters = new ArrayList<>();

	public ClustersWizardForm() {

	}

	public ClustersWizardForm(Input input) {
		this.clusters = input.getNetwork().getClusters();
	}

	public void enrichInput(Input input) {
		input.getNetwork().setClusters(this.clusters);
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

}
