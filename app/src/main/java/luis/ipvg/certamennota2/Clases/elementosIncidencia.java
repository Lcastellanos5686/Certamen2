package luis.ipvg.certamennota2.Clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import luis.ipvg.certamennota2.R;

public class elementosIncidencia extends RecyclerView.Adapter<elementosIncidencia.elementoViewHolder> {
    private List<FormGeo> listaForms;

    public elementosIncidencia(List<FormGeo> lista) {
        this.listaForms = lista;
    }

    public static class elementoViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvDescripcion;

        public elementoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }

    @NonNull
    @Override
    public elementoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_incidencia, parent, false);

        return new elementoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull elementoViewHolder holder, int position) {

        FormGeo incidencia = listaForms.get(position);

        holder.tvTitulo.setText(incidencia.getTitulo());
        holder.tvDescripcion.setText(incidencia.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaForms.size();
    }



}
