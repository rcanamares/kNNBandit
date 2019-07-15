/* 
 * Copyright (C) 2019 Information Retrieval Group at Universidad Autónoma
 * de Madrid, http://ir.ii.uam.es.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0.
 * 
 */
package es.uam.eps.ir.knnbandit.recommendation.knn.user;


import es.uam.eps.ir.knnbandit.data.preference.index.fast.FastUpdateableItemIndex;
import es.uam.eps.ir.knnbandit.data.preference.index.fast.FastUpdateableUserIndex;
import es.uam.eps.ir.knnbandit.recommendation.knn.similarities.UpdateableSimilarity;
import es.uam.eps.ir.ranksys.fast.preference.SimpleFastPreferenceData;

/**
 * Incremental version of user-based kNN algorithm.
 * @author Javier Sanz-Cruzado (javier.sanz-cruzado@uam.es)
 * @author Pablo Castells (pablo.castells@uam.es)
 * @param <U> Type of the users.
 * @param <I> Type of the items.
 */
public class InteractiveUserBasedKNN<U,I> extends AbstractInteractiveUserBasedKNN<U,I>
{
   
    
    /**
     * Constructor.
     * @param uIndex user index.
     * @param iIndex item index.
     * @param prefData preference data.
     * @param ignoreUnknown true if we must ignore unknown items when updating.
     * @param ignoreZeroes true if we ignore zero ratings when updating.
     * @param k number of neighbors to use.
     * @param sim updateable similarity
     */
    public InteractiveUserBasedKNN(FastUpdateableUserIndex<U> uIndex, FastUpdateableItemIndex<I> iIndex, SimpleFastPreferenceData<U, I> prefData, boolean ignoreUnknown, boolean ignoreZeroes, int k, UpdateableSimilarity sim)
    {
        super(uIndex, iIndex, prefData, ignoreUnknown, ignoreZeroes, k, sim);
    }
    
    /**
     * Constructor.
     * @param uIndex user index.
     * @param iIndex item index.
     * @param prefData preference data.
     * @param ignoreUnknown true if we must ignore unknown items when updating.
     * @param k number of neighbors to use.
     * @param sim updateable similarity
     */
    public InteractiveUserBasedKNN(FastUpdateableUserIndex<U> uIndex, FastUpdateableItemIndex<I> iIndex, SimpleFastPreferenceData<U, I> prefData, boolean ignoreUnknown, boolean ignoreZeroes, boolean notReciprocal, int k, UpdateableSimilarity sim)
    {
        super(uIndex, iIndex, prefData, ignoreUnknown, ignoreZeroes, notReciprocal, k, sim);
    }
    
    @Override
    public void updateMethod(int uidx, int iidx, double value)
    {
        this.trainData.getIidxPreferences(iidx).forEach(vidx -> 
        {
            this.sim.update(uidx, vidx.v1, iidx, value, vidx.v2);
        });
    }

    @Override
    protected double score(int vidx, double rating)
    {
       return rating;
    }
}
