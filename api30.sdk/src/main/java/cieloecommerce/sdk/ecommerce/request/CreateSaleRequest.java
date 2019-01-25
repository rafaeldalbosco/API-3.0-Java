package cieloecommerce.sdk.ecommerce.request;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;

import com.google.gson.GsonBuilder;

import cieloecommerce.sdk.Environment;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.Sale;

/**
 * Create any kind of sale
 */
public class CreateSaleRequest extends AbstractSaleRequest<Sale, Sale> {
	public CreateSaleRequest(Merchant merchant, Environment environment) {
		super(merchant, environment);
	}

	@Override
	public Sale execute(Sale param) throws IOException, CieloRequestException {
		String url = environment.getApiUrl() + "1/sales/";
		HttpPost request = new HttpPost(url);

		EntityBuilder entityBuilder = EntityBuilder.create();
		entityBuilder.setContentType(ContentType.APPLICATION_JSON);
		entityBuilder.setContentEncoding("UTF-8");
		entityBuilder.setText(new GsonBuilder().create().toJson(param));
		request.setEntity(entityBuilder.build());

		HttpResponse response = sendRequest(request);

		return readResponse(response, Sale.class);
	}
}
