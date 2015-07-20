/**
 * 
 */
package br.com.mobgui4so.model.guigenerating.creator;

import java.util.List;

import br.com.mobgui4so.model.guigenerating.Gene;
import br.com.mobgui4so.model.guigenerating.Gene.ParamType;
import br.com.mobgui4so.model.guigenerating.Genotype;
import br.com.mobgui4so.model.guigenerating.codon.BaseCodon;
import br.com.mobgui4so.model.guigenerating.codon.LayoutCodon;
import br.com.mobgui4so.model.guigenerating.pojo.Individual;
import br.com.mobgui4so.model.guigenerating.pojo.Population;
import br.com.mobgui4so.model.pojo.SOServiceParam;
import br.com.mobgui4so.model.pojo.SmartObject;
import br.com.mobgui4so.model.pojo.SmartObjectService;

/**
 * @author Ercilio Nascimento
 */
public class Creator {

	public Population create(int populationSize, SmartObject so) {
		Population population = new Population();
		for (int i = 0; i < populationSize; i++) {
			population.addIndividual(create(so));
		}

		return population;
	}

	private Individual create(SmartObject so) {
		Individual individual = new Individual();
		Genotype genotype = new Genotype();
		Gene root = genotype.createRoot();
		int id = 0;

		for (SmartObjectService service : so.getServices()) {
			Gene frameLayout = new Gene(ParamType.LAYOUT, service.getServiceName());
			frameLayout.setId(++id);
			LayoutCodon codon = (LayoutCodon) frameLayout.getCodon();
			codon.setFrame(true);
			codon.setWeight(1f);
			if (service.getIdServiceModbus().equals("03")) {
				codon.setGetLayout(true);
			}
			codon.setMutable(true);
			root.addChild(frameLayout);
			for (SOServiceParam param : service.getParams()) {
				Gene paramLayout = new Gene(ParamType.LAYOUT);
				paramLayout.setId(++id);
				paramLayout.setParamLayout(true);
				BaseCodon layoutCodon = paramLayout.getCodon();
				layoutCodon.setWeight(1f);
				frameLayout.addChild(paramLayout);
				Gene paramGene = new Gene(param.getType(), param.getName(), param.getMinValue(), param.getMaxValue(), param.getOptions());
				paramGene.setId(++id);
				BaseCodon paramCodon = paramGene.getCodon();
				paramCodon.setWeight(.5f);
				paramLayout.addChild(paramGene);
			}
		}
		individual.setGenotype(genotype);

		return individual;

	}

	private Individual createTestGenotype(SmartObject so) {
		Individual ind = new Individual();
		Genotype test = new Genotype();
		Gene root = test.createRoot();
		int id = 0;
		SmartObjectService service = so.getServices().get(0);
		List<SOServiceParam> params = service.getParams();

		Gene layout = new Gene(ParamType.LAYOUT, service.getServiceName());
		layout.setId(++id);
		LayoutCodon codon = (LayoutCodon) layout.getCodon();
		codon.setOrientation(1);
		codon.setFrame(true);
		root.addChild(layout);

		Gene layout2 = new Gene(ParamType.LAYOUT, "");
		layout2.setId(++id);
		layout.addChild(layout2);

		Gene param1 = new Gene(params.get(0).getType(), params.get(0).getName());
		param1.setId(++id);
		layout2.addChild(param1);

		Gene layout3 = new Gene(ParamType.LAYOUT, "");
		layout3.setId(++id);
		layout.addChild(layout3);

		Gene param2 = new Gene(params.get(1).getType(), params.get(1).getName());
		param2.setId(++id);
		layout3.addChild(param2);

		Gene layout4 = new Gene(ParamType.LAYOUT, "Limpar");
		layout4.setId(++id);
		LayoutCodon codon2 = (LayoutCodon) layout4.getCodon();
		codon2.setOrientation(1);
		codon2.setFrame(true);
		root.addChild(layout4);

		Gene layout5 = new Gene(ParamType.LAYOUT, "");
		layout5.setId(++id);
		layout4.addChild(layout5);

		Gene param3 = new Gene(ParamType.DOUBLE, "Temperatura");
		param3.setId(++id);
		layout5.addChild(param3);

		Gene layout6 = new Gene(ParamType.LAYOUT, "Explodir");
		layout6.setId(++id);
		LayoutCodon codon3 = (LayoutCodon) layout6.getCodon();
		codon3.setOrientation(1);
		codon3.setFrame(true);
		root.addChild(layout6);

		Gene layout7 = new Gene(ParamType.LAYOUT, "");
		layout7.setId(++id);
		layout6.addChild(layout7);

		Gene param4 = new Gene(ParamType.BOOLEAN, "Emo��o");
		param4.setId(++id);
		layout7.addChild(param4);

		ind.setGenotype(test);

		return ind;
	}
}
